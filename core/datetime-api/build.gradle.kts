plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.metro)
    id("ganzhornfest")
}

android {
    namespace = "de.heilsen.ganzhornfest.datetime"
    compileSdk = 37
    defaultConfig.minSdk = 24
    // AndroidDateTimeFormatter uses java.time, which needs API 26.
    // Core library desugaring makes it work down to minSdk 24.
    compileOptions.isCoreLibraryDesugaringEnabled = true
}

kotlin.jvmToolchain(21)

dependencies {
    implementation(libs.kotlinx.datetime)
    coreLibraryDesugaring(libs.desugar.jdk.libs)
}
