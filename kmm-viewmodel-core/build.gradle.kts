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
    val macosX64 = macosX64()
    val macosArm64 = macosArm64()
    val iosArm64 = iosArm64()
    val iosX64 = iosX64()
    val iosSimulatorArm64 = iosSimulatorArm64()
    val watchosArm32 = watchosArm32()
    val watchosArm64 = watchosArm64()
    val watchosX64 = watchosX64()
    val watchosSimulatorArm64 = watchosSimulatorArm64()
    val watchosDeviceArm64 = watchosDeviceArm64()
    val tvosArm64 = tvosArm64()
    val tvosX64 = tvosX64()
    val tvosSimulatorArm64 = tvosSimulatorArm64()
    androidTarget {
        publishLibraryVariants("release")
    }
    //endregion
    //region Other targets
    val jvm = jvm()
    val js = js {
        browser()
        nodejs()
    }
    val linuxArm64 = linuxArm64()
    val linuxX64 = linuxX64()
    val mingwX64 = mingwX64()
    //endregion

    sourceSets {
        all {
            languageSettings.optIn("kotlin.RequiresOptIn")
            languageSettings.optIn("com.rickclephas.kmm.viewmodel.InternalKMMViewModelApi")
        }

        val commonMain by getting {
            dependencies {
                api(libs.kotlinx.coroutines.core)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

        val androidMain by getting {
            dependencies {
                api(libs.androidx.lifecycle.viewmodel.ktx)
            }
        }

        val appleMain by creating {
            dependsOn(commonMain)
        }
        val appleTest by creating {
            dependsOn(commonTest)
        }
        listOf(
            macosX64, macosArm64,
            iosArm64, iosX64, iosSimulatorArm64,
            watchosArm32, watchosArm64, watchosX64, watchosSimulatorArm64, watchosDeviceArm64,
            tvosArm64, tvosX64, tvosSimulatorArm64
        ).forEach {
            getByName("${it.targetName}Main") {
                dependsOn(appleMain)
            }
            getByName("${it.targetName}Test") {
                dependsOn(appleTest)
            }
            it.compilations.getByName("main") {
                cinterops.create("KMMViewModelCoreObjC") {
                    includeDirs("$projectDir/../KMMViewModelCoreObjC")
                }
            }
        }

        val otherMain by creating {
            dependsOn(commonMain)
        }
        val otherTest by creating {
            dependsOn(commonTest)
        }
        listOf(jvm, js, linuxArm64, linuxX64, mingwX64).forEach {
            getByName("${it.targetName}Main") {
                dependsOn(otherMain)
            }
            getByName("${it.targetName}Test") {
                dependsOn(otherTest)
            }
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
