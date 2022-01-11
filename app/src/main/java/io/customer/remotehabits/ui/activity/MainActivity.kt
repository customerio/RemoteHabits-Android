package io.customer.remotehabits.ui.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import io.customer.remotehabits.ui.RHApp
import io.customer.remotehabits.ui.navigation.NavigationManager
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigationManager: NavigationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val navController = rememberNavController()
            navigationManager.commands.collectAsState().value.also { command ->
                if (command.destination.isNotEmpty()) {
                    navController.navigate(command.destination)
                }
            }
            RHApp(navController)
        }
    }
}
