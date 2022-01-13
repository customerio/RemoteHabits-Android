package io.customer.remotehabits.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.customer.remotehabits.ui.home.HomeRoute
import io.customer.remotehabits.ui.login.LoginRoute

sealed class Screen(val route: String) {
    object Authentication : Screen("auth")
    object Login : Screen("login")
    object Dashboard : Screen("dashboard")
}

sealed class LeafScreen(val route: String) {

    fun createRoute(root: Screen) = "${root.route}/$route"

    object Hydration : LeafScreen("hydration")
}

@Composable
fun RHNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Login.route
) {

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        addLoginRoute(navController)
        addDashboardRoute(navController)
    }
}

internal fun NavGraphBuilder.addLoginRoute(
    navController: NavController
) {
    composable(Screen.Login.route) {
        LoginRoute()
    }
}

internal fun NavGraphBuilder.addDashboardRoute(
    navController: NavController
) {
    composable(Screen.Dashboard.route) {
        HomeRoute()
    }
}
