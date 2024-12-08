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
    version = "1.0.0-BETA-7-kotlin-2.1.0-RC2"

    repositories {
        mavenCentral()
        google()
    }
}
