plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
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
    api(libs.dagger)
    kapt(libs.dagger.compiler)
    api(libs.sqldelight.android.driver)
    api(libs.sqldelight.coroutines.extensions)
}

sqldelight {
    databases {
        create("GanzhornfestDb") {
            packageName.set(android.namespace)
        }
    }
}