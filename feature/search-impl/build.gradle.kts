plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "de.heilsen.ganzhornfest.search.impl"
    compileSdk = 36
    defaultConfig.minSdk = 24
}

kotlin {
    jvmToolchain(21)
}

dependencies {
    implementation(project(":feature:search-api"))
    implementation(project(":core-api"))
    implementation(project(":presenter-api"))
    implementation(project(":core:datetime-api"))
    implementation(project(":data"))
    implementation(project(":theme"))
    implementation(libs.molecule.runtime)
    debugImplementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.foundation.android)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.dagger)
    implementation(libs.timber)
    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.kotlinx.datetime)
    testImplementation(libs.kotest.runner.junit5)
    testImplementation(libs.mockk)
    testImplementation(libs.turbine)
}