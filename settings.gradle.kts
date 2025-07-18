@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
    }
}

rootProject.name = "kmp-observableviewmodel"

include(":kmp-observableviewmodel-annotations")
include(":kmp-observableviewmodel-core")
include(":kmp-observableviewmodel-properties")
