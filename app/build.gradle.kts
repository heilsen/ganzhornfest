import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.metro)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.sqldelight)
}

android {
    namespace = "de.heilsen.ganzhornfest"
    compileSdk = 37

    defaultConfig {
        applicationId = "de.heilsen.ganzhornfest"
        minSdk = 24
        targetSdk = 37
        versionCode = 202601
        versionName = "2026.1.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        androidResources {
            @Suppress("UnstableApiUsage")
            localeFilters += setOf("de")
        }

        //TODO: don't read properties in configuration phase
        val localProperties = readProperties("local.properties")

        resValue("string", "google_maps_key", localProperties["google_maps_key"] as String)
    }

    val keystoreProps = rootProject.file("keystore.properties")
        .takeIf { it.exists() }
        ?.let { readProperties("keystore.properties") }
    val signingConfigName = (findProperty("signingConfig") as String?) ?: "release"
    require(signingConfigName in setOf("release", "upload")) {
        "Unknown -PsigningConfig='$signingConfigName'. Use 'release' or 'upload'."
    }

    signingConfigs {
        create("release") {
            if (keystoreProps != null) {
                storeFile = file(keystoreProps["storeFile"] as String)
                storePassword = keystoreProps["storePassword"] as String
                keyAlias = keystoreProps["release.keyAlias"] as String
                keyPassword = keystoreProps["release.keyPassword"] as String
            }
        }
        create("upload") {
            if (keystoreProps != null) {
                storeFile = file(keystoreProps["storeFile"] as String)
                storePassword = keystoreProps["storePassword"] as String
                keyAlias = keystoreProps["upload.keyAlias"] as String
                keyPassword = keystoreProps["upload.keyPassword"] as String
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles += listOf(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                file("proguard-rules.pro")
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
    implementation(project(":feature:countdown"))
    implementation(project(":theme"))

    implementation(libs.javax.inject)
    implementation(libs.timber)

    implementation(libs.kotlinx.coroutines)
    implementation(libs.kotlinx.serialization)
    implementation(libs.kotlinx.collections.immutable)

    implementation(libs.kotlinx.datetime)

    implementation(libs.bundles.androidx)

    implementation(libs.bundles.androidx.compose)
    debugImplementation(libs.bundles.androidx.compose.debug)

    implementation(libs.play.services.maps)
    implementation(libs.google.maps.compose)

    lintChecks(libs.compose.lint.checks)

    testImplementation(libs.kotest.runner.junit5)
    testImplementation(kotlin("test-junit"))
    testImplementation(kotlin("reflect"))
    testImplementation(libs.mockk)
    testImplementation(libs.turbine)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.ui.test.junit4)
}

private fun readProperties(fileName: String): Properties {
    val propertiesFile = rootProject.file(fileName)
    return Properties().apply {
        FileInputStream(propertiesFile).use {
            load(it)
        }
    }
}
