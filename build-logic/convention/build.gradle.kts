plugins {
    `kotlin-dsl`
}

group = "de.heilsen.ganzhornfest.buildlogic"

kotlin {
    jvmToolchain(21)
}

dependencies {
    // implementation, not compileOnly. Consumers do not otherwise have ktlint on
    // their classpath, so the convention plugin must carry it for pluginManager.apply.
    implementation(libs.ktlint.gradlePlugin)
    // Same reason: the convention plugin configures the Compose compiler extension, so it needs
    // the plugin on its own classpath to resolve the extension type.
    implementation(libs.kotlin.composeGradlePlugin)
}
