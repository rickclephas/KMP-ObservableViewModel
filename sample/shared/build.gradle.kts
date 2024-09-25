plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.ksp)
    alias(libs.plugins.nativecoroutines)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    androidTarget()
    jvmToolchain(11)
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "KMPObservableViewModelSampleShared"
        }
    }

    sourceSets {
        all {
            languageSettings {
                optIn("kotlin.experimental.ExperimentalObjCName")
                optIn("kotlinx.cinterop.ExperimentalForeignApi")
            }
        }
        commonMain {
            dependencies {
                implementation(libs.kotlinx.coroutines.core)
                api("com.rickclephas.kmp:kmp-observableviewmodel-core")

                implementation(compose.runtime)
                implementation(compose.ui)
                implementation(compose.material)
            }
        }
        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}

android {
    namespace = "com.rickclephas.kmp.observableviewmodel.sample.shared"
    compileSdk = 33
    defaultConfig {
        minSdk = 28
    }
}

tasks.findByName("checkSandboxAndWriteProtection")?.dependsOn("syncComposeResourcesForIos")
