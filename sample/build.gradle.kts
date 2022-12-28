buildscript {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}

allprojects {
    repositories {
        mavenCentral()
        google()
        maven("https://androidx.dev/storage/compose-compiler/repository/")
    }
}
