plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "de.heilsen.ganzhornfest.core"
    compileSdk = 37
    defaultConfig.minSdk = 24
}
kotlin.jvmToolchain(21)

dependencies {
    implementation(project(":di-api"))

    implementation(libs.kotlinx.coroutines)

    implementation(libs.kotlinx.collections.immutable)

    implementation(libs.dagger)

    api(libs.kotlinx.datetime)
    implementation(libs.androidx.annotation)
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)
}