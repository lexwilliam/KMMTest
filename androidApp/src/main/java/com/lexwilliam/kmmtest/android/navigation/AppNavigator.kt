package com.lexwilliam.kmmtest.android.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lexwilliam.kmmtest.android.add.AddRoute
import com.lexwilliam.kmmtest.android.home.HomeRoute

@Composable
fun AppNavigator(controller: NavHostController = rememberNavController()) {
    NavHost(navController = controller, startDestination = "home") {
        composable("home") {
            HomeRoute(
                toAdd = { controller.navigate("add") }
            )
        }
        composable("add") {
            AddRoute(
                toHome = { controller.navigate("home") }
            )
        }
    }
}