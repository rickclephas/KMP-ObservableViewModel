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
    version = "1.0.0-ALPHA-15-kotlin-2.0.0-Beta1"

    repositories {
        mavenCentral()
        google()
    }
}
