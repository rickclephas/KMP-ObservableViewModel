plugins {
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
}

buildscript {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}

allprojects {
    group = "com.rickclephas.kmp"
    version = "1.0.0-BETA-8-kotlin-2.1.10-RC"

    repositories {
        mavenCentral()
        google()
    }
}
