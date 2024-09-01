plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.anvil)
}

android {
    namespace = "de.heilsen.ganzhornfest.program"
    compileSdk = 34
    defaultConfig {
        minSdk = 21
    }

    buildFeatures {
        compose = true
        shaders = false
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
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

anvil {
    generateDaggerFactories = true
}

dependencies {
    implementation(project(":core"))
    implementation(project(":base-presenter"))
    implementation(project(":database"))
    implementation(project(":di-api"))
    implementation(project(":theme"))

    implementation("com.google.dagger:dagger:2.47")

    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.5")
    implementation(libs.kotlinx.datetime)

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
    implementation("androidx.compose.ui:ui:1.4.3")
    implementation(libs.androidx.compose.foundation.android)
    implementation("androidx.compose.material3:material3:1.1.1")
    implementation("androidx.compose.ui:ui-tooling-preview:1.4.3")
    implementation(project(":data"))
    debugImplementation(libs.androidx.compose.ui.tooling)

}