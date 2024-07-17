import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.multiplatform)
    `kmp-observableviewmodel-publish`
    id("kotlinx-atomicfu")
}

kotlin {
    explicitApi()
    jvmToolchain(11)

    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    applyDefaultHierarchyTemplate {
        common {
            group("androidx") {
                withAndroidTarget()
                group("ios")
                withJvm()
                withLinuxX64()
                group("macos")
            }
            group("nonAndroidx") {
                withJs()
                withLinuxArm64()
                group("mingw")
                group("tvos")
                withWasmJs()
                group("watchos")
            }
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

    listOf(
        macosX64(), macosArm64(),
        iosArm64(), iosX64(), iosSimulatorArm64(),
        watchosArm32(), watchosArm64(), watchosX64(), watchosSimulatorArm64(), watchosDeviceArm64(),
        tvosArm64(), tvosX64(), tvosSimulatorArm64(),
    ).forEach {
        it.compilations.getByName("main") {
            cinterops.create("KMPObservableViewModelCoreObjC") {
                includeDirs("$projectDir/../KMPObservableViewModelCoreObjC")
            }
        }
    }
    androidTarget {
        publishLibraryVariants("release")
    }
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
                optIn("kotlinx.cinterop.ExperimentalForeignApi")
            }
        }

        commonMain {
            dependencies {
                api(libs.kotlinx.coroutines.core)
            }
        }
        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

        val androidxMain by getting {
            dependencies {
                api(libs.androidx.lifecycle.viewmodel)
            }
        }
    }
}

android {
    namespace = "com.rickclephas.kmp.observableviewmodel"
    compileSdk = 33
    defaultConfig {
        minSdk = 19
    }
    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}
