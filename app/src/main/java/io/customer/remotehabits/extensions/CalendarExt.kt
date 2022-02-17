package io.customer.remotehabits.extensions

import io.customer.remotehabits.data.models.HabitTime
import java.util.*

fun Calendar.getHabitTime(): HabitTime {
    val currentHour: Int = this.get(Calendar.HOUR_OF_DAY)
    val currentMinute: Int = this.get(Calendar.MINUTE)
    return HabitTime(minute = currentMinute, hour = currentHour)
}

fun getTimeStampFromMinuteAndHour(minute: Int, hour: Int): Long {
    val cal = Calendar.getInstance()
    cal.set(Calendar.MINUTE, minute)
    cal.set(Calendar.HOUR_OF_DAY, hour)
    return cal.timeInMillis
}

fun getHabitTime(timestamp: Long?): HabitTime {
    return if (timestamp == null) {
        Calendar.getInstance().getHabitTime()
    } else {
        val date = Date(timestamp)
        val cal = Calendar.getInstance()
        cal.time = date
        cal.getHabitTime()
    }
}
