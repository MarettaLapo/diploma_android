pluginManagement {
    repositories {
        google()
        jcenter()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
//        maven { url 'https://jitpack.io' }
        maven {
            url = uri("https://jitpack.io")
        }
        google()
//        mavenCentral()
        jcenter()
    }
}
rootProject.name = "Diploma"
include(":app")
 