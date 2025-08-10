plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.anvil)
}

android {
    namespace = "de.heilsen.ganzhornfest.core"
    compileSdk = 36
    defaultConfig {
        minSdk = 24
    }
}
kotlin {
    jvmToolchain(17)
}

anvil {
    addOptionalAnnotations = true
    generateDaggerFactories = true
}

dependencies {
    api(project(":core-api"))
    api(project(":di-api"))

    implementation(libs.kotlinx.coroutines)

    implementation(libs.kotlinx.collections.immutable)

    api(libs.dagger)

    api(libs.kotlinx.datetime)
    implementation(libs.androidx.annotation)
    implementation(libs.androidx.compose.ui.tooling.preview)
}