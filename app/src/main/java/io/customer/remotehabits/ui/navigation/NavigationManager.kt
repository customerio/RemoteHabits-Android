package io.customer.remotehabits.ui.navigation

import io.customer.remotehabits.ui.navigation.LoginDirections.default
import kotlinx.coroutines.flow.MutableStateFlow

class NavigationManager {

    var commands = MutableStateFlow(default)

    fun navigate(
        directions: NavigationCommand
    ) {
        commands.value = directions
    }
}
