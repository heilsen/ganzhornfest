import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.metro)
    id("ganzhornfest")
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
}

android {
    namespace = "de.heilsen.ganzhornfest"
    compileSdk = 37

    defaultConfig {
        applicationId = "de.heilsen.ganzhornfest"
        minSdk = 24
        targetSdk = 37
        versionCode = 2026022
        versionName = "2026.2.2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        androidResources {
            @Suppress("UnstableApiUsage")
            localeFilters += setOf("de")
        }

        val mapsKey =
            providers
                .environmentVariable("GANZHORNFEST_MAPS_KEY")
                .orElse(providers.gradleProperty("ganzhornfest.mapsKey"))
                .getOrElse("")

        resValue("string", "google_maps_key", mapsKey)
    }

    val keystoreProps =
        rootProject
            .file("keystore.properties")
            .takeIf { it.exists() }
            ?.let { readProperties("keystore.properties") }
    val signingConfigName = (findProperty("signingConfig") as String?) ?: "release"
    require(signingConfigName in setOf("release", "upload")) {
        "Unknown -PsigningConfig='$signingConfigName'. Use 'release' or 'upload'."
    }

    // Both configs are declared even when only one key is on hand. CI carries the
    // upload key alone, so a missing entry leaves that config unconfigured instead
    // of failing configuration.
    signingConfigs {
        create("release") {
            keystoreProps.signingKeys("release")?.let { keys ->
                storeFile = file(keys.storeFile)
                storePassword = keys.storePassword
                keyAlias = keys.keyAlias
                keyPassword = keys.keyPassword
            }
        }
        create("upload") {
            keystoreProps.signingKeys("upload")?.let { keys ->
                storeFile = file(keys.storeFile)
                storePassword = keys.storePassword
                keyAlias = keys.keyAlias
                keyPassword = keys.keyPassword
            }
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles +=
                listOf(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    file("proguard-rules.pro"),
                )
            signingConfig = signingConfigs.getByName(signingConfigName)
        }
    }

    buildFeatures {
        buildConfig = true
        resValues = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    lint {
        lintConfig = file("lint.xml")
    }

    testOptions {
        unitTests {
            isReturnDefaultValues = true
            all { test ->
                test.apply {
                    useJUnitPlatform()
                }
            }
        }
    }

    // So unit tests can read the shipped seed data straight off the classpath.
    sourceSets.getByName("test").resources.srcDir("src/main/assets")
}

kotlin {
    jvmToolchain(21)
}

dependencies {
    implementation(project(":presenter-api"))
    implementation(project(":bus-api"))
    implementation(project(":bus-impl"))
    implementation(project(":core-api"))
    implementation(project(":core-impl"))
    implementation(project(":data"))
    implementation(project(":database"))
    implementation(project(":di-api"))
    implementation(project(":feature:search-api"))
    implementation(project(":feature:search-impl"))
    implementation(project(":info-api"))
    implementation(project(":map"))
    implementation(project(":program"))
    implementation(project(":theme"))

    implementation(libs.javax.inject)
    implementation(libs.timber)

    implementation(libs.kotlinx.coroutines)
    implementation(libs.kotlinx.serialization)
    implementation(libs.kotlinx.collections.immutable)

    implementation(libs.kotlinx.datetime)

    implementation(libs.bundles.androidx)

    implementation(libs.bundles.androidx.compose)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.compose.material3.adaptive.navigation.suite)
    debugImplementation(libs.bundles.androidx.compose.debug)

    implementation(libs.play.services.maps)
    implementation(libs.google.maps.compose)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)

    testImplementation(libs.kotest.runner.junit5)
    testImplementation(kotlin("test-junit"))
    testImplementation(kotlin("reflect"))
    testImplementation(libs.mockk)
    testImplementation(libs.turbine)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // play-services-maps still pins androidx.fragment 1.0.0. Nothing else in the graph
    // raises it, so the shipped bundle carries a 2018 Fragment and Play Console flags it.
    // No code here touches a Fragment API, so a constraint is enough.
    constraints {
        implementation(libs.androidx.fragment)
    }
}

private class SigningKeys(
    val storeFile: String,
    val storePassword: String,
    val keyAlias: String,
    val keyPassword: String,
)

/**
 * Reads the store plus the `<name>.keyAlias` and `<name>.keyPassword` pair.
 * Returns null when any of them is missing, so a keystore holding only one of
 * the two keys still configures.
 */
private fun Properties?.signingKeys(name: String): SigningKeys? {
    val props = this ?: return null
    return SigningKeys(
        storeFile = props.getProperty("storeFile") ?: return null,
        storePassword = props.getProperty("storePassword") ?: return null,
        keyAlias = props.getProperty("$name.keyAlias") ?: return null,
        keyPassword = props.getProperty("$name.keyPassword") ?: return null,
    )
}

private fun readProperties(fileName: String): Properties {
    val propertiesFile = rootProject.file(fileName)
    return Properties().apply {
        FileInputStream(propertiesFile).use {
            load(it)
        }
    }
}
