pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Ganzhornfest"
include(
    ":app",
    ":presenter-api",
    ":bus-api",
    ":bus-impl",
    ":core-api",
    ":core-impl",
    ":data",
    ":database",
    ":di-api",
    ":info-api",
    ":map",
    ":program",
    ":theme"
)

gradle.lifecycle.beforeProject {
    group = "de.heilsen.ganzhornfest"
}