plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "de.heilsen.ganzhornfest.bus"
    compileSdk = 36
    defaultConfig {
        minSdk = 24
    }

    buildFeatures {
        compose = true
        shaders = false
    }

    kotlinOptions {
        freeCompilerArgs += listOf(
            "-P",
            "plugin:androidx.compose.compiler.plugins.kotlin:stabilityConfigurationPath=" +
                    "${rootProject.projectDir.absolutePath}/compose_compiler_config.conf"
        )
    }

}
kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(project(":bus-api"))
    implementation(project(":core-api"))
    implementation(project(":presenter-api"))
    implementation(project(":database"))
    implementation(project(":di-api"))
    implementation(project(":theme"))

    implementation(libs.dagger)

    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.kotlinx.datetime)

    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.foundation.android)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)

}