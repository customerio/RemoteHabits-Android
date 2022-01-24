package io.customer.remotehabits.ui.navigation

import androidx.navigation.NamedNavArgument

object LoginDirections {

    val default = object : NavigationCommand {

        override val arguments = emptyList<NamedNavArgument>()

        override val destination = ""
    }

    val login = object : NavigationCommand {

        override val arguments = emptyList<NamedNavArgument>()

        override val destination = Screen.Login.route
    }

    val dashboard = object : NavigationCommand {

        override val arguments = emptyList<NamedNavArgument>()

        override val destination = Screen.Dashboard.route
    }
}
