plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "de.heilsen.ganzhornfest.base.presenter"
    compileSdk = 36
    defaultConfig.minSdk = 24
}

kotlin.jvmToolchain(21)

dependencies {
    implementation(libs.kotlinx.coroutines)

    implementation(libs.molecule.runtime)
    api(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.compose.ui)

    implementation(libs.kotlinx.collections.immutable)
}