plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.metro)
}

android {
    namespace = "de.heilsen.ganzhornfest.datetime"
    compileSdk = 37
    defaultConfig.minSdk = 24
}

kotlin.jvmToolchain(21)

dependencies {
    implementation(libs.kotlinx.datetime)
}