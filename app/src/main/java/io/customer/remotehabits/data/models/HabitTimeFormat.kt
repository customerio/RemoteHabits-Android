package io.customer.remotehabits.data.models

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

sealed class HabitTimeFormat(val dateFormat: DateFormat) {

    companion object {
        const val defaultTimePlaceHolder = "--:--"
    }

    fun getFormattedDate(date: Date): String {
        return try {
            dateFormat.format(date)
        } catch (exception: Exception) {
            defaultTimePlaceHolder
        }
    }

    class Time12Hour :
        HabitTimeFormat(dateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault()))
}
