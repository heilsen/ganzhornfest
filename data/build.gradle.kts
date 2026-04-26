plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.metro)
}

android {
    namespace = "de.heilsen.ganzhornfest.data"
    compileSdk = 37
    defaultConfig.minSdk = 24
}
kotlin.jvmToolchain(21)

dependencies {
    api(project(":database"))

    implementation(libs.javax.inject)
    implementation(libs.sqldelight.coroutines.extensions)
}

