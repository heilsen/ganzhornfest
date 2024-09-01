plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.anvil)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.sqldelight)
}

android {
    namespace = "de.heilsen.ganzhornfest.database"
    compileSdk = 34
    defaultConfig {
        minSdk = 21
    }
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    api(project(":core"))
    api(project(":di-api"))
    api(project(":di-impl"))
    api("com.google.dagger:dagger:2.47")
    api("app.cash.sqldelight:android-driver:2.0.0")
    api("app.cash.sqldelight:coroutines-extensions:2.0.0")
}

anvil {
    generateDaggerFactories = true
    addOptionalAnnotations = true
}

sqldelight {
    databases {
        create("GanzhornfestDb") {
            packageName.set(android.namespace)
        }
    }
}