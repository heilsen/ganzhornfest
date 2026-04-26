plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.metro)
    alias(libs.plugins.sqldelight)
}

android {
    namespace = "de.heilsen.ganzhornfest.database"
    compileSdk = 37
    defaultConfig.minSdk = 24
}

kotlin.jvmToolchain(21)

dependencies {
    api(project(":core-api"))
    api(project(":di-api"))
    api(libs.sqldelight.android.driver)
    api(libs.sqldelight.coroutines.extensions)
}

sqldelight {
    databases {
        create("GanzhornfestDb") {
            packageName = android.namespace
            schemaOutputDirectory = file("src/main/sqldelight/databases")
        }
    }
}