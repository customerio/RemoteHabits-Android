package io.customer.remotehabits.data.models

data class HabitTime(
    val minute: Int,
    val hour: Int,
    val timeFormat: HabitTimeFormat = HabitTimeFormat.Time12Hour()
)
