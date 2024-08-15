plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
}

apply(from = "$rootDir/feature-dependencies.gradle")
apply(from = "$rootDir/android-config.gradle")
apply(from = "$rootDir/test-dependencies.gradle")
apply(from = "$rootDir/test-dependencies-jvm.gradle")

android.namespace = "com.ai.notelist"

dependencies {
    implementation(project(":common"))
    implementation(project(":common-android-data"))
}