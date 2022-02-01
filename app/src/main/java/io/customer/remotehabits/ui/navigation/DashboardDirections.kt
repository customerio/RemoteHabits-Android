package io.customer.remotehabits.ui.navigation

import androidx.navigation.NamedNavArgument
import io.customer.remotehabits.data.models.HabitType

object DashboardDirections {

    fun habitDetail(habitType: HabitType) = object : NavigationCommand {

        override val arguments = emptyList<NamedNavArgument>()

        override val destination =
            "LeafScreen.HabitDetails.createRoute(Screen.Dashboard, habitType.name)"
    }
}
