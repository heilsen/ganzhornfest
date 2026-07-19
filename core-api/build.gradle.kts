plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.metro)
    id("ganzhornfest")
}

android {
    namespace = "de.heilsen.ganzhornfest.core.api"
    compileSdk = 37
    defaultConfig.minSdk = 24
}
kotlin.jvmToolchain(21)

dependencies {
    implementation(project(":di-api"))

    implementation(libs.kotlinx.coroutines)

    implementation(libs.kotlinx.collections.immutable)

    api(libs.kotlinx.datetime)
    implementation(libs.androidx.annotation)
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)
}
