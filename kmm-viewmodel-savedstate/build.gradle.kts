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

    //region Apple and Android targets
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
    androidTarget {
        publishLibraryVariants("release")
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
                optIn("kotlinx.cinterop.ExperimentalForeignApi")
            }
        }

        commonMain {
            dependencies {
                api(project(":kmm-viewmodel-core"))
                api(libs.kotlinx.coroutines.core)
                api(libs.kotlinx.serialization.core)
                implementation(libs.kotlinx.serialization.cbor)
            }
        }

        androidMain {
            dependencies {
                api(libs.androidx.lifecycle.viewmodel.savedstate)
            }
        }
    }
}

android {
    namespace = "com.rickclephas.kmm.viewmodel.savedstate"
    compileSdk = 33
    defaultConfig {
        minSdk = 14
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
