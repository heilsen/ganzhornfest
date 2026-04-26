plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "de.heilsen.ganzhornfest.data"
    compileSdk = 37
    defaultConfig.minSdk = 24
}
kotlin.jvmToolchain(21)

dependencies {
    api(project(":database"))

    implementation(libs.dagger)

    implementation(libs.sqldelight.coroutines.extensions)
}

