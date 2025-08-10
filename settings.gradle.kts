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
include(":app")
include(":presenter-api")
include(":bus-api")
include(":bus-impl")
include(":core")
include(":data")
include(":database")
include(":di-api")
include(":di-impl")
include(":map")
include(":program")
include(":theme")

gradle.lifecycle.beforeProject {
    group = "de.heilsen.ganzhornfest"
}