plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.metro)
    id("ganzhornfest")
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "de.heilsen.ganzhornfest.map"
    compileSdk = 37
    defaultConfig.minSdk = 24

    testOptions {
        unitTests {
            isReturnDefaultValues = true
            all { test ->
                test.useJUnitPlatform()
            }
        }
    }
}

kotlin.jvmToolchain(21)

dependencies {
    implementation(project(":core-api"))
    implementation(project(":presenter-api"))
    implementation(project(":data"))
    implementation(project(":database"))
    implementation(project(":di-api"))
    implementation(project(":theme"))

    implementation(libs.javax.inject)
    implementation(libs.timber)

    implementation(libs.play.services.maps)
    implementation(libs.google.maps.compose)

    implementation(libs.kotlinx.coroutines)
    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.kotlinx.datetime)

    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.foundation.android)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)

    testImplementation(libs.kotest.runner.junit5)
    testImplementation(libs.mockk)
    testImplementation(libs.molecule.runtime)
    testImplementation(libs.turbine)
    testImplementation(libs.kotlinx.coroutines.test)
}
