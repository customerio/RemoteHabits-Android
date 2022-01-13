package io.customer.remotehabits.ui.navigation

import androidx.navigation.NamedNavArgument

object LoginDirections {

    val Default = object : NavigationCommand {

        override val arguments = emptyList<NamedNavArgument>()

        override val destination = ""
    }

    val dashboard = object : NavigationCommand {

        override val arguments = emptyList<NamedNavArgument>()

        override val destination = Screen.Dashboard.route
    }
}
