package io.customer.remotehabits.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.systemBarsPadding
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import io.customer.remotehabits.ui.login.AuthenticationViewModel
import io.customer.remotehabits.ui.navigation.RHNavGraph
import io.customer.remotehabits.ui.theme.AppTheme

@Composable
fun RHApp(
    navController: NavHostController,
    authenticationViewModel: AuthenticationViewModel = hiltViewModel()
) {

    AppTheme {
        ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
            val systemUiController = rememberSystemUiController()
            val darkIcons = MaterialTheme.colors.isLight

            SideEffect {
                systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = darkIcons)
            }

            val navBackStackEntry by navController.currentBackStackEntryAsState()

            val currentRoute =
                navBackStackEntry?.destination?.route ?: authenticationViewModel.getDestination()

            RHNavGraph(
                startDestination = currentRoute,
                modifier = Modifier.systemBarsPadding()
            )
        }
    }
}
