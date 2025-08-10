plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "de.heilsen.ganzhornfest.data"
    compileSdk = 36
    defaultConfig {
        minSdk = 24
    }
}
kotlin {
    jvmToolchain(17)
}

dependencies {
    api(project(":database"))

    implementation(libs.dagger)

    implementation(libs.sqldelight.coroutines.extensions)
}

