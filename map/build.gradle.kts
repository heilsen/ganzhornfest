plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "de.heilsen.ganzhornfest.map"
    compileSdk = 36
    defaultConfig {
        minSdk = 24
    }

    buildFeatures {
        compose = true
        shaders = false
    }

}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(project(":core-api"))
    implementation(project(":presenter-api"))
    implementation(project(":data"))
    implementation(project(":database"))
    implementation(project(":di-api"))
    implementation(project(":theme"))

    implementation(libs.dagger)

    implementation(libs.play.services.maps)
    implementation(libs.google.maps.compose)

    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.kotlinx.datetime)

    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.foundation.android)
    implementation(libs.androidx.compose.material)
    debugImplementation(libs.androidx.compose.ui.tooling)

}