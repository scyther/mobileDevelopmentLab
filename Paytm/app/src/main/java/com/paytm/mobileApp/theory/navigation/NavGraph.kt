package com.paytm.mobileApp.theory.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.paytm.mobileApp.theory.navigation.screens.Home
import com.paytm.mobileApp.theory.navigation.screens.Login
import com.paytm.mobileApp.theory.navigation.screens.Welcome

@Composable
fun NavGraph() {

    // navController -> Handle Navigation
    // rememberNavController -> Create & Remember navController
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.Login
    ) {
        composable<NavRoutes.Login> {
            Login(navController)
        }

        composable<NavRoutes.Home> {
            Home(navController)
        }

        // backStackEntry is a Lambda Function having data and arguments which are passed during Navigation
        composable<NavRoutes.Welcome> { backStackEntry ->
            val data = backStackEntry.toRoute<NavRoutes.Welcome>()

            Welcome(data.userName, navController)
        }
    }
}