plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.metro)
    id("ganzhornfest")
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "de.heilsen.di.api"
    compileSdk = 37
    defaultConfig.minSdk = 24
}

kotlin.jvmToolchain(21)

dependencies {
    implementation(libs.androidx.compose.ui)
}
