plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.metro)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "de.heilsen.ganzhornfest.theme"
    compileSdk = 37
    defaultConfig.minSdk = 24
}
kotlin.jvmToolchain(21)

dependencies {
    implementation(project(":core-api"))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.foundation.android)
}