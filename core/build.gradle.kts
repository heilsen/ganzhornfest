plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.anvil)
}

android {
    namespace = "de.heilsen.ganzhornfest.core"
    compileSdk = 34
    defaultConfig {
        minSdk = 21
    }
}
kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(project(":di-api"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.5")

    implementation("com.google.dagger:dagger:2.47")

    api(libs.kotlinx.datetime)
    implementation("androidx.annotation:annotation:1.8.0")
    implementation(libs.androidx.ui.tooling.preview.android)
}