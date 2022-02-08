package io.customer.remotehabits.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import io.customer.remotehabits.data.models.HabitType
import io.customer.remotehabits.ui.detail.HabitDetailRoute
import io.customer.remotehabits.ui.home.HomeRoute
import io.customer.remotehabits.ui.login.LoginRoute
import io.customer.remotehabits.ui.navigation.LeafScreen.Companion.ARGS_HABIT_CATEGORY

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Dashboard : Screen("dashboard")
}

sealed class LeafScreen(private val route: String) {

    companion object {
        const val ARGS_HABIT_CATEGORY = "category"
    }

    fun createRoute(root: Screen) = "${root.route}/$route"

    object HabitDetails : Screen("habit/{category}") {

        fun createRoute(type: HabitType): String {
            return "habit/${type.name}"
        }
    }
}

@Composable
fun RHNavGraph(
    modifier: Modifier = Modifier,
    startDestination: String = Screen.Login.route
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        addLoginRoute(navController)
        addDashboardRoute(navController)
        addHabitDetailRoute(navController)
    }
}

internal fun NavGraphBuilder.addHabitDetailRoute(
    navController: NavHostController,
) {
    composable(
        route = LeafScreen.HabitDetails.route,
        arguments = listOf(
            navArgument(ARGS_HABIT_CATEGORY) { type = NavType.StringType }
        )
    ) {
        val habitType = it.arguments?.getString("category")
        requireNotNull(habitType) { "$ARGS_HABIT_CATEGORY parameter wasn't found. Please make sure it's set!" }
        HabitDetailRoute(
            onBackPressed = {
                navController.navigateUp()
            }
        )
    }
}

internal fun NavGraphBuilder.addLoginRoute(
    navController: NavHostController,
) {
    composable(Screen.Login.route) {
        LoginRoute(
            onLoginSuccess = {
                navController.navigate(Screen.Dashboard.route) {
                    launchSingleTop = true
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            }
        )
    }
}

internal fun NavGraphBuilder.addDashboardRoute(
    navController: NavHostController,
) {
    composable(Screen.Dashboard.route) {
        HomeRoute(
            openHabitDetail = {
                navController.navigate(LeafScreen.HabitDetails.createRoute(it.type))
            },
            onLogout = {
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Dashboard.route) { inclusive = true }
                }
            }
        )
    }
}
