package io.customer.remotehabits.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.customer.remotehabits.R
import io.customer.remotehabits.utils.*

@Entity
class Habit(
    @PrimaryKey
    val name: String,
    @ColumnInfo(name = "status") val status: Boolean = false,
    @ColumnInfo(name = "type") val type: HabitType,
    @ColumnInfo(name = "caption") val caption: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "reminder_count") val reminderCount: Int = 0,
    @ColumnInfo(name = "reminder_start_time") val startTime: Long? = null,
    @ColumnInfo(name = "reminder_end_time") val endTime: Long? = null
) {

    fun copy(
        name: String = this.name,
        status: Boolean = this.status,
        type: HabitType = this.type,
        startTime: Long? = this.startTime,
        description: String = this.description,
        caption: String = this.caption,
        endTime: Long? = this.endTime,
        reminderCount: Int = this.reminderCount
    ) = Habit(
        name = name,
        status = status,
        type = type,
        startTime = startTime,
        description = description,
        caption = caption,
        endTime = endTime,
        reminderCount = reminderCount
    )
}

enum class HabitType {
    Hydration, TakingBreaks, FocusTime;

    fun getIcon(): Int {
        return when (this) {
            Hydration -> R.drawable.ic_hydration
            TakingBreaks -> R.drawable.ic_breaks
            FocusTime -> R.drawable.ic_focus
        }
    }

    fun getSwitchTestTag(): String {
        return when (this) {
            Hydration -> HYDRATION_TOGGLE
            TakingBreaks -> TAKING_BREAK_TOGGLE
            FocusTime -> FOCUS_TIME_TOGGLE
        }
    }

    fun getListTestTag(): String {
        return when (this) {
            Hydration -> HYDRATION_LIST_ITEM
            TakingBreaks -> TAKING_BREAK_LIST_ITEM
            FocusTime -> FOCUS_TIME_LIST_ITEM
        }
    }
}
