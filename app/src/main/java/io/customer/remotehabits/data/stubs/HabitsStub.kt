package io.customer.remotehabits.data.stubs

import android.content.Context
import io.customer.remotehabits.R
import io.customer.remotehabits.data.models.Habit
import io.customer.remotehabits.data.models.HabitType

class HabitsStub {

    companion object {

        fun getHabits(context: Context): Array<Habit> {
            return arrayOf(
                Habit(
                    name = context.resources.getString(R.string.hydration),
                    caption = context.getString(R.string.hydration_caption),
                    description = context.getString(R.string.hydration_decription),
                    type = HabitType.Hydration
                ),
                Habit(
                    name = context.resources.getString(R.string.taking_breaks),
                    caption = context.getString(R.string.taking_breaks_caption),
                    description = context.getString(R.string.taking_breaks_decription),
                    type = HabitType.TakingBreaks
                ),
                Habit(
                    name = context.resources.getString(R.string.focus_time),
                    caption = context.getString(R.string.focus_time_caption),
                    description = context.getString(R.string.focus_time_decription),
                    type = HabitType.FocusTime
                )
            )
        }
    }
}
