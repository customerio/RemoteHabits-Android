package io.customer.remotehabits.extensions

import com.google.android.material.timepicker.TimeFormat
import io.customer.remotehabits.data.models.HabitTime
import io.customer.remotehabits.data.models.HabitTimeFormat

fun HabitTime.getPickerTimeFormant(): Int {
    return when (this.timeFormat) {
        is HabitTimeFormat.Time12Hour -> TimeFormat.CLOCK_12H
    }
}
