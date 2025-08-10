plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "de.heilsen.ganzhornfest.core"
    compileSdk = 36
    defaultConfig {
        minSdk = 24
    }
}
kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(project(":di-api"))

    implementation(libs.kotlinx.coroutines)

    implementation(libs.kotlinx.collections.immutable)

    implementation(libs.dagger)

    api(libs.kotlinx.datetime)
    implementation(libs.androidx.annotation)
    implementation(libs.androidx.compose.ui.tooling.preview)
}