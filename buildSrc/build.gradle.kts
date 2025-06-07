plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.android.library.gradle.plugin)
    implementation(libs.vanniktech.mavenPublish)
}
