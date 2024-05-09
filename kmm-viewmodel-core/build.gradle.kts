import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.android.library)
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.kotlin.multiplatform)
    `kmm-viewmodel-publish`
}

kotlin {
    explicitApi()
    jvmToolchain(11)

    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    applyDefaultHierarchyTemplate {
        common {
            group("androidx") {
                withAndroidTarget()
                withJvm()
                withLinuxX64()
            }
            group("other") {
                withJs()
                withLinuxArm64()
                group("mingw")
                withWasm()
            }
            group("nonApple") {
                withAndroidTarget()
                withJvm()
                withJs()
                group("linux")
                group("mingw")
                withWasm()
            }
        }
    }

    //region Apple and Android targets
    listOf(
        macosX64(), macosArm64(),
        iosArm64(), iosX64(), iosSimulatorArm64(),
        watchosArm32(), watchosArm64(), watchosX64(), watchosSimulatorArm64(), watchosDeviceArm64(),
        tvosArm64(), tvosX64(), tvosSimulatorArm64(),
    ).forEach {
        it.compilations.getByName("main") {
            cinterops.create("KMMViewModelCoreObjC") {
                includeDirs("$projectDir/../KMMViewModelCoreObjC")
            }
        }
    }
    androidTarget {
        publishLibraryVariants("release")
    }
    //endregion
    //region Other targets
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
    //endregion

    targets.all {
        compilations.all {
            compilerOptions.configure {
                freeCompilerArgs.add("-Xexpect-actual-classes")
            }
        }
    }

    sourceSets {
        all {
            languageSettings {
                optIn("com.rickclephas.kmm.viewmodel.InternalKMMViewModelApi")
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
    namespace = "com.rickclephas.kmm.viewmodel"
    compileSdk = 33
    defaultConfig {
        minSdk = 19
    }
    // TODO: Remove workaround for https://issuetracker.google.com/issues/260059413
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}
