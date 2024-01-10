pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}

rootProject.name = "kmm-viewmodel"

include(":kmm-viewmodel-core")
include(":kmm-viewmodel-savedstate")
