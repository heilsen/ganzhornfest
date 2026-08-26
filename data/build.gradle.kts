plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.metro)
    id("ganzhornfest")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "de.heilsen.ganzhornfest.data"
    compileSdk = 37
    defaultConfig.minSdk = 24

    testOptions {
        unitTests {
            all { test ->
                test.useJUnitPlatform()
            }
        }
    }
}
kotlin.jvmToolchain(21)

dependencies {
    api(project(":database"))

    implementation(libs.javax.inject)
    implementation(libs.sqldelight.coroutines.extensions)
    implementation(libs.kotlinx.serialization)

    testImplementation(libs.kotest.runner.junit5)
    testImplementation(libs.sqldelight.sqlite.driver)
}
