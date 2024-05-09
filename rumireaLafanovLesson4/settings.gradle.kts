pluginManagement {
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

rootProject.name = "ru.mirea.Lafanov.Lesson4"
include(":app")
include(":pleer")
include(":thread")
include(":data_thread")
include(":looper")
include(":cryptoloader")
include(":serviceapp")
include(":data_thread")
include(":workmanager")
