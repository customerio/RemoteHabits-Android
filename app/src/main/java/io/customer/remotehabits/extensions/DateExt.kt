package io.customer.remotehabits.extensions

import io.customer.remotehabits.data.models.HabitTimeFormat
import java.util.*

fun Date.getFormattedTime(habitTimeFormat: HabitTimeFormat = HabitTimeFormat.Time12Hour()): String {
    return habitTimeFormat.getFormattedDate(this)
}
