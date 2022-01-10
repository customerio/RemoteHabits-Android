package io.customer.remotehabits.ui.navigation

import io.customer.remotehabits.ui.navigation.LoginDirections.Default
import kotlinx.coroutines.flow.MutableStateFlow

class NavigationManager {

    var commands = MutableStateFlow(Default)

    fun navigate(
        directions: NavigationCommand
    ) {
        commands.value = directions
    }
}
