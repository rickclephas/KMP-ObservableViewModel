pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://androidx.dev/storage/compose-compiler/repository/")
    }
}

rootProject.name = "sample"

include(":androidApp")
include(":shared")

includeBuild("..") {
    dependencySubstitution {
        substitute(module("com.rickclephas.kmp:kmp-observableviewmodel-core"))
            .using(project(":kmp-observableviewmodel-core"))
    }
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}
