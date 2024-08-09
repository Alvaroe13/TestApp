plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
}

apply(from = "$rootDir/feature-dependencies.gradle")
apply(from = "$rootDir/android-config.gradle")

android.namespace = "com.ai.main"

dependencies {
    implementation(project(":common"))
}