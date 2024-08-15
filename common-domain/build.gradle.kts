plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("kotlin-kapt")
    kotlin("plugin.serialization")
}

apply(from = "$rootDir/test-dependencies-jvm.gradle")

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    api(libs.javax.inject.kmm)
    api(libs.kotlin.serialization)
}