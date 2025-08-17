plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "de.heilsen.ganzhornfest.info.api"
    compileSdk = 36
    defaultConfig.minSdk = 24
}
