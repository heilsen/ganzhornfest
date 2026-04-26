plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.metro)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "de.heilsen.ganzhornfest.program"
    compileSdk = 37
    defaultConfig.minSdk = 24
}

kotlin.jvmToolchain(21)

dependencies {
    implementation(project(":core-api"))
    implementation(project(":presenter-api"))
    implementation(project(":database"))
    implementation(project(":di-api"))
    implementation(project(":theme"))

    implementation(libs.javax.inject)
    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.kotlinx.datetime)

    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.foundation.android)
    implementation(libs.androidx.compose.material)
    implementation(project(":data"))
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)

}