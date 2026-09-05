plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.metro)
    id("ganzhornfest")
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
    // material3 1.4.0 dropped the transitive material-icons-core.
    implementation(libs.androidx.compose.material.icons.core)
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.foundation.android)
    // api so isSidePanelLayout callers do not each need the adaptive artifact.
    api(libs.androidx.compose.material3.adaptive)
}
