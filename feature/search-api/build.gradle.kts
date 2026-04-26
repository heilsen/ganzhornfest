plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "de.heilsen.ganzhornfest.search.api"
    compileSdk = 37
    defaultConfig.minSdk = 24
}

kotlin {
    jvmToolchain(21)
}

dependencies {
    implementation(project(":core-api"))
    implementation(project(":core:datetime-api"))
    implementation(project(":theme"))

    debugImplementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.foundation.android)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.dagger)
    implementation(libs.kotlinx.datetime)
}