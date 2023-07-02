plugins {
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.android.library) apply false
    @Suppress("DSL_SCOPE_VIOLATION")
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
    group = "com.rickclephas.kmm"
    version = "1.0.0-ALPHA-10-kotlin-1.9.0-RC"

    repositories {
        mavenCentral()
        google()
    }
}
