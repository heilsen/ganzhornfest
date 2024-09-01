plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "de.heilsen.ganzhornfest.base.presenter"
    compileSdk = 34
    defaultConfig {
        minSdk = 21
    }

    buildFeatures {
        buildConfig = false
        compose = true
        shaders = false
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
}
kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

    implementation("app.cash.molecule:molecule-runtime:1.1.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("androidx.compose.ui:ui:1.4.3")

    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.5")
}