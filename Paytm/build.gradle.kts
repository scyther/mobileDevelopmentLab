// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false

    kotlin("jvm") version "2.4.21"
    kotlin("plugin.serialization") version "2.4.21"

    // KSP (Kotlin Symbol Processing) plugin for Room database compiler
    // Note: If Android Studio flags a version mismatch with Kotlin, align this version to match your Kotlin compiler
    id("com.google.devtools.ksp") version "2.3.10" apply false
}