plugins {
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.android.application)
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.rickclephas.kmm.viewmodel.sample"
    compileSdk = 33
    defaultConfig {
        applicationId = "com.rickclephas.kmm.viewmodel.sample"
        minSdk = 28
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.0"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.activity.compose)
}