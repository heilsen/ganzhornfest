import java.io.FileInputStream
import java.util.*

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.anvil)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.molecule)
}

android {
    namespace = "de.heilsen.ganzhornfest"
    compileSdk = 34

    defaultConfig {
        applicationId = "de.heilsen.ganzhornfest"
        minSdk = 24
        targetSdk = 34
        versionCode = 202401
        versionName = "2024.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        resourceConfigurations.add("de")

        //TODO: don't read properties in configuration phase
        val localProperties = readProperties("local.properties")

        resValue("string", "google_maps_key", localProperties["google_maps_key"] as String)
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles += listOf(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                file("proguard-rules.pro")
            )
        }
    }

    buildFeatures {
        buildConfig = true
        compose = true
        shaders = false
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    lint {
        lintConfig = file("lint.xml")
    }

    @Suppress("UnstableApiUsage")
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
    jvmToolchain(17)
}

anvil {
    addOptionalAnnotations = true
}

dependencies {
    implementation(project(":base-presenter"))
    implementation(project(":bus"))
    implementation(project(":core"))
    implementation(project(":data"))
    implementation(project(":database"))
    implementation(project(":di-api"))
    implementation(project(":di-impl"))
    implementation(project(":map"))
    implementation(project(":program"))
    implementation(project(":theme"))

    kapt(libs.dagger.compiler)
    kaptAndroidTest(libs.dagger.compiler)
    implementation(libs.dagger)

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

//    lintChecks(libs.compose.lint.checks)

    testImplementation(libs.kotest.runner.junit5)
    testImplementation(kotlin("test-junit"))
    testImplementation(kotlin("reflect"))
    testImplementation(libs.mockk)
    testImplementation(libs.turbine)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.ui.test.junit4)
}

kapt {
    correctErrorTypes = true
}

fun readProperties(fileName: String): Properties {
    val propertiesFile = rootProject.file(fileName)
    return Properties().apply {
        FileInputStream(propertiesFile).use {
            load(it)
        }
    }
}