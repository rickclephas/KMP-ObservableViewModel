import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    id("kmp-observableviewmodel-android-library")
    id("kmp-observableviewmodel-kotlin-multiplatform")
    id("kmp-observableviewmodel-publish")
}

kotlin {
    explicitApi()
    jvmToolchain(11)

    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    applyDefaultHierarchyTemplate {
        common {
            group("nonApple") {
                withAndroidTarget()
                withJvm()
                withJs()
                group("linux")
                group("mingw")
                withWasmJs()
            }
        }
    }

    macosX64()
    macosArm64()
    iosArm64()
    iosX64()
    iosSimulatorArm64()
    watchosArm32()
    watchosArm64()
    watchosX64()
    watchosSimulatorArm64()
    watchosDeviceArm64()
    tvosArm64()
    tvosX64()
    tvosSimulatorArm64()
    androidTarget()
    jvm()
    js {
        browser()
        nodejs()
    }
    linuxArm64()
    linuxX64()
    mingwX64()
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        nodejs()
        d8()
    }

    targets.all {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    freeCompilerArgs.add("-Xexpect-actual-classes")
                }
            }
        }
    }

    sourceSets {
        all {
            languageSettings {
                optIn("com.rickclephas.kmp.observableviewmodel.InternalKMPObservableViewModelApi")
                optIn("kotlin.experimental.ExperimentalObjCRefinement")
                optIn("kotlinx.cinterop.ExperimentalForeignApi")
            }
        }

        commonMain {
            dependencies {
                api(project(":kmp-observableviewmodel-core"))
            }
        }
        appleMain {
            dependencies {
                api(libs.nativecoroutines.core)
            }
        }
    }
}

android {
    namespace = "com.rickclephas.kmp.observableviewmodel.properties"
    compileSdk = 33
    defaultConfig {
        minSdk = 19
    }
}
