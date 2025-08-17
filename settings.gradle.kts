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
    ":bus-api",
    ":bus-impl",
    ":core-api",
    ":core-impl",
    ":core:datetime-api",
    ":data",
    ":database",
    ":di-api",
    ":feature:search-api",
    ":feature:search-impl",
    ":info-api",
    ":map",
    ":presenter-api",
    ":program",
    ":theme",
)

gradle.lifecycle.beforeProject {
    group = "de.heilsen.ganzhornfest"
}