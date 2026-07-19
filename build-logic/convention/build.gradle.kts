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
}
