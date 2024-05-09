plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.ksp)
    alias(libs.plugins.nativecoroutines)
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
            baseName = "KMMViewModelSampleShared"
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
                api("com.rickclephas.kmm:kmm-viewmodel-core")
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
    namespace = "com.rickclephas.kmm.viewmodel.sample.shared"
    compileSdk = 33
    defaultConfig {
        minSdk = 28
    }
    // TODO: Remove workaround for https://issuetracker.google.com/issues/260059413
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
