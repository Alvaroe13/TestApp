plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
}

apply(from = "$rootDir/feature-dependencies.gradle")
apply(from = "$rootDir/android-config.gradle")

android.namespace = "com.ai.notelist"

dependencies {
    implementation(project(":common"))
    implementation(project(":common-android-data"))

    testImplementation("io.mockk:mockk:1.13.12")
    testImplementation("io.mockk:mockk-android:1.13.12")
    testImplementation("io.mockk:mockk-agent:1.13.12")
    testImplementation(libs.testng)
    testImplementation(libs.coroutines.test)
}