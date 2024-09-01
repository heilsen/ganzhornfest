plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "de.heilsen.ganzhornfest.data"
    compileSdk = 34
    defaultConfig {
        minSdk = 21
    }
}
kotlin {
    jvmToolchain(17)
}

dependencies {
    api(project(":database"))

    implementation("com.google.dagger:dagger:2.47")

    implementation("app.cash.sqldelight:coroutines-extensions:2.0.0")
}

