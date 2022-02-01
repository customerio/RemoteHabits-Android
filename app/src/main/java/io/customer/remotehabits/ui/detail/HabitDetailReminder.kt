package io.customer.remotehabits.ui.detail

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import io.customer.remotehabits.R
import io.customer.remotehabits.data.models.Habit
import io.customer.remotehabits.extensions.findActivity
import io.customer.remotehabits.extensions.getMinuteAndHour
import io.customer.remotehabits.extensions.getTimeStampFromMinuteAndHour
import io.customer.remotehabits.ui.component.RemoteHabitCard
import io.customer.remotehabits.ui.theme.RHTheme

@Composable
fun HabitDetailReminder(
    habit: Habit,
    onHabitReminderCountUpdate: (habit: Habit, count: Int) -> Unit,
    onHabitStartTimeUpdate: (habit: Habit, time: Long) -> Unit,
    onHabitEndTimeUpdate: (habit: Habit, time: Long) -> Unit
) {

    val context = LocalContext.current

    RemoteHabitCard(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.reminders),
                style = RHTheme.typography.h3,
                color = RHTheme.colors.textPrimary
            )
            ReminderListItem(
                title = stringResource(id = R.string.how_many_times_per_day),
                textFieldText = habit.reminderCount.toLong(),
                onItemUpdate = {
                    val count = if (it.isDigitsOnly() && it.isNotEmpty()) {
                        it.toInt()
                    } else 0
                    onHabitReminderCountUpdate.invoke(
                        habit, count
                    )
                }
            )
            ReminderListItem(
                title = stringResource(id = R.string.from),
                textFieldText = habit.startTime,
                isTimePicker = true,
                showTimePicket = {
                    showDatePicker(context, habit.startTime) {
                        onHabitStartTimeUpdate.invoke(habit, it)
                    }
                }
            )
            ReminderListItem(
                title = stringResource(id = R.string.to),
                textFieldText = habit.endTime,
                isTimePicker = true,
                showTimePicket = {
                    showDatePicker(context, habit.endTime) {
                        onHabitEndTimeUpdate.invoke(habit, it)
                    }
                }
            )
        }
    }
}

private fun showDatePicker(
    context: Context,
    date: Long?,
    onTimeSelected: (Long) -> Unit
) {
    val activity = context.findActivity()
    val minuteAndHour = getMinuteAndHour(date)
    val picker = MaterialTimePicker.Builder()
        .setTimeFormat(TimeFormat.CLOCK_12H)
        .setMinute(minuteAndHour.first)
        .setHour(minuteAndHour.second).build()
    activity.let {
        picker.show(it.supportFragmentManager, picker.toString())
        picker.addOnPositiveButtonClickListener {
            val time = getTimeStampFromMinuteAndHour(picker.minute, picker.hour)
            onTimeSelected.invoke(time)
        }
    }
}

@Composable
fun ReminderTimeBox(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier
            .border(
                border = BorderStroke(
                    width = 1.dp, color = RHTheme.colors.underline
                ),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        content()
    }
}
