package io.customer.remotehabits.extensions

import java.text.SimpleDateFormat
import java.util.*

val timeFormat: SimpleDateFormat by lazy { SimpleDateFormat("hh:mm a", Locale.getDefault()) }

const val defaultTimePlaceHolder = "--:--"

fun Date.getFormattedTime(): String {
    return try {
        timeFormat.format(this.time)
    } catch (exception: Exception) {
        defaultTimePlaceHolder
    }
}
