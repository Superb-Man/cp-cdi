// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
    }
    dependencies {
        val navVersion = "2.9.0"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$navVersion")
        // safe args
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:2.9.0")

    }
}
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
}
