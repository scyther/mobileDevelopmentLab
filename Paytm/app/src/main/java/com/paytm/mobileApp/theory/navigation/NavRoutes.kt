package com.paytm.mobileApp.theory.navigation

import kotlinx.serialization.Serializable

// https://developer.android.com/guide/navigation#kts

//Step 1:   Add these dependencies

// Add these in project level Gradle file
//kotlin("jvm") version "2.2.0"
//kotlin("plugin.serialization") version "2.2.0"

// Add these in module level Gradle file
// In Plugins add:
//noinspection NewerVersionAvailable
//id("org.jetbrains.kotlin.plugin.serialization") version "2.2.0"

//In dependencies add:
//// For Navigation
////noinspection UseTomlInstead
//implementation("androidx.navigation:navigation-compose:2.10.2")
//
//// Serialization
////noinspection UseTomlInstead,NewerVersionAvailable
//implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")


// Step2:  Create Serializable Routes
// Sealed class creates a restricted hierarchy (its subclasses can only be defined in the same file)
// Each subclass of sealed class can have its own data and behavior
@Serializable
sealed class NavRoutes {

    @Serializable
    object Home : NavRoutes()

    @Serializable
    object Login: NavRoutes()

    @Serializable
    data class Welcome(val userName: String) : NavRoutes()
}
