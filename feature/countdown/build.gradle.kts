plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.metro)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "de.heilsen.ganzhornfest.countdown"
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

kotlin {
    jvmToolchain(21)
}

dependencies {
    implementation(project(":core-api"))
    implementation(project(":presenter-api"))
    implementation(project(":theme"))

    implementation(libs.javax.inject)
    implementation(libs.kotlinx.datetime)
    implementation(libs.molecule.runtime)

    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.foundation.android)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)

    testImplementation(libs.kotest.runner.junit5)
}
