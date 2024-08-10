plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

apply(from = "$rootDir/feature-dependencies.gradle")
apply(from = "$rootDir/android-config.gradle")

android.namespace = "com.ai.common_android_data"

dependencies {
    implementation(project(":common-domain"))
}