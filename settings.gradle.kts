pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "TemplateProject2"
include(":app")
include(":core:data")
include(":core:utils")
include(":core")
include(":core:network")
include(":core:db")
include(":core:model")
include(":core:domain")
include(":core:notifications")
include(":core:datastore")
include(":feature")
include(":feature:login")
include(":feature:repositories")
