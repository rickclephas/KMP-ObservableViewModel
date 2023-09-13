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
    applyDefaultHierarchyTemplate()

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
    //endregion

    sourceSets {
        all {
            languageSettings {
                optIn("com.rickclephas.kmm.viewmodel.InternalKMMViewModelApi")
                optIn("kotlinx.cinterop.ExperimentalForeignApi")
                compilerOptions.freeCompilerArgs.add("-Xexpect-actual-classes")
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

        androidMain {
            dependencies {
                api(libs.androidx.lifecycle.viewmodel.ktx)
            }
        }

        val otherMain by creating {
            dependsOn(commonMain.get())
        }
        val otherTest by creating {
            dependsOn(commonTest.get())
        }
        jvmMain {
            dependsOn(otherMain)
        }
        jvmTest {
            dependsOn(otherTest)
        }
        jsMain {
            dependsOn(otherMain)
        }
        jsTest {
            dependsOn(otherTest)
        }
        linuxMain {
            dependsOn(otherMain)
        }
        linuxTest {
            dependsOn(otherTest)
        }
        mingwMain {
            dependsOn(otherMain)
        }
        mingwTest {
            dependsOn(otherTest)
        }
    }
}

android {
    namespace = "com.rickclephas.kmm.viewmodel"
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
