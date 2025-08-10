plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "de.heilsen.ganzhornfest.base.presenter"
    compileSdk = 36
    defaultConfig {
        minSdk = 24
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
    implementation(libs.kotlinx.coroutines)

    implementation(libs.molecule.runtime)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.compose.ui)

    implementation(libs.kotlinx.collections.immutable)
}