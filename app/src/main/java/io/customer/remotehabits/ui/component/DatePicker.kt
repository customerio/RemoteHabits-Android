package io.customer.remotehabits.ui.component

import android.content.Context
import com.google.android.material.timepicker.MaterialTimePicker
import io.customer.remotehabits.data.models.HabitTime
import io.customer.remotehabits.extensions.findActivity
import io.customer.remotehabits.extensions.getPickerTimeFormant
import io.customer.remotehabits.extensions.getTimeStampFromMinuteAndHour

fun showDatePicker(
    context: Context,
    habitTime: HabitTime,
    onTimeSelected: (Long) -> Unit
) {
    val activity = context.findActivity()
    val picker = MaterialTimePicker.Builder()
        .setTimeFormat(habitTime.getPickerTimeFormant())
        .setMinute(habitTime.minute)
        .setHour(habitTime.hour).build()
    activity.let {
        picker.show(it.supportFragmentManager, picker.toString())
        picker.addOnPositiveButtonClickListener {
            val time = getTimeStampFromMinuteAndHour(picker.minute, picker.hour)
            onTimeSelected.invoke(time)
        }
    }
}
