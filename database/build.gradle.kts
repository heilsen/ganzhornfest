plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.anvil)
    alias(libs.plugins.sqldelight)
}

android {
    namespace = "de.heilsen.ganzhornfest.database"
    compileSdk = 36
    defaultConfig {
        minSdk = 24
    }
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    api(project(":core-api"))
    api(project(":di-api"))
    api(project(":di-impl"))
    api(libs.dagger)
    api(libs.sqldelight.android.driver)
    api(libs.sqldelight.coroutines.extensions)
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