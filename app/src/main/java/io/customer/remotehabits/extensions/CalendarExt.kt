package io.customer.remotehabits.extensions

import java.util.*

fun Calendar.getMinuteAndHour(): Pair<Int, Int> {
    val currentHour: Int = this.get(Calendar.HOUR_OF_DAY)
    val currentMinute: Int = this.get(Calendar.MINUTE)
    return currentMinute to currentHour
}

fun getTimeStampFromMinuteAndHour(minute: Int, hour: Int): Long {
    val cal = Calendar.getInstance()
    cal.set(Calendar.MINUTE, minute)
    cal.set(Calendar.HOUR_OF_DAY, hour)
    return cal.timeInMillis
}

fun getMinuteAndHour(timestamp: Long?): Pair<Int, Int> {
    return if (timestamp == null) {
        Calendar.getInstance().getMinuteAndHour()
    } else {
        val date = Date(timestamp)
        val cal = Calendar.getInstance()
        cal.time = date
        cal.getMinuteAndHour()
    }
}
