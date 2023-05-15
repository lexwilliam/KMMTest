package com.lexwilliam.kmmtest.android.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.lexwilliam.kmmtest.android.add.AddRoute
import com.lexwilliam.kmmtest.android.detail.DetailRoute
import com.lexwilliam.kmmtest.android.home.HomeRoute

@Composable
fun AppNavigator(controller: NavHostController = rememberNavController()) {
    NavHost(navController = controller, startDestination = "home") {
        composable("home") {
            HomeRoute(
                toAdd = { controller.navigate("add") },
                toDetail = { transaction_id ->
                    controller.navigate("detail".plus("/?transaction_id=$transaction_id")
                    )
                }
            )
        }
        composable("add") {
            AddRoute(
                toHome = { controller.navigate("home") }
            )
        }
        composable(
            route = "detail".plus("/?transaction_id={transaction_id}"),
            arguments = listOf(
                navArgument("transaction_id") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) {
            DetailRoute(
                toHome = { controller.navigate("home")}
            )
        }
    }
}