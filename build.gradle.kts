buildscript {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://plugins.gradle.org/m2/") }
        maven { url = uri("https://jitpack.io") }
    }
    dependencies {
        classpath(libs.gradle.plugin)
        classpath(libs.kotlin.plugin)
        classpath(libs.androidx.nav.safe.args.plugin)
        classpath(libs.dagger.plugin)
        classpath(libs.kotlin.serialization.plugin)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://plugins.gradle.org/m2/") }
        maven { url = uri("https://jitpack.io") }
    }
}