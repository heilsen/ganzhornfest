plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
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