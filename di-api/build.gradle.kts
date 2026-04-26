plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "de.heilsen.di.api"
    compileSdk = 37
    defaultConfig.minSdk = 24
}

kotlin.jvmToolchain(21)

dependencies {
    api(libs.dagger)
    implementation(libs.androidx.compose.ui)
}