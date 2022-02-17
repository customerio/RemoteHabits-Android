package io.customer.remotehabits.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.customer.remotehabits.data.models.Habit
import io.customer.remotehabits.data.models.HabitType
import io.customer.remotehabits.data.repositories.EventsRepository
import io.customer.remotehabits.data.repositories.HabitRepository
import io.customer.remotehabits.utils.AnalyticsConstants.END_TIME
import io.customer.remotehabits.utils.AnalyticsConstants.REMINDER_COUNT
import io.customer.remotehabits.utils.AnalyticsConstants.START_TIME
import io.customer.remotehabits.utils.AnalyticsConstants.STATUS
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HabitDetailUiState(
    val loading: Boolean = false,
    val habit: Habit? = null
)

@HiltViewModel
class HabitDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val habitRepository: HabitRepository,
    private val eventsRepository: EventsRepository,
) : ViewModel() {

    private val habitType =
        savedStateHandle.get<String>("category") ?: HabitType.Hydration.name

    // UI state exposed to the UI
    private val _uiState = MutableStateFlow(HabitDetailUiState(loading = true))
    val uiState: StateFlow<HabitDetailUiState> = _uiState.asStateFlow()

    init {
        loadData(HabitType.valueOf(habitType))
    }

    fun onStateChanged(habit: Habit, checked: Boolean) {
        viewModelScope.launch {
            habitRepository.updateHabit(habit.copy(status = checked))
            eventsRepository.track(name = habit.name, attributes = mapOf(STATUS to checked))
        }
    }

    fun onReminderCountChanged(habit: Habit, count: Int) {
        viewModelScope.launch {
            habitRepository.updateHabit(habit.copy(reminderCount = count))
            eventsRepository.track(name = habit.name, attributes = mapOf(REMINDER_COUNT to count))
        }
    }

    fun onReminderStartTimeChanged(habit: Habit, time: Long) {
        viewModelScope.launch {
            habitRepository.updateHabit(habit.copy(startTime = time))
            eventsRepository.track(name = habit.name, attributes = mapOf(START_TIME to time))
        }
    }

    fun onReminderEndTimeChanged(habit: Habit, time: Long) {
        viewModelScope.launch {
            habitRepository.updateHabit(habit.copy(endTime = time))
            eventsRepository.track(name = habit.name, attributes = mapOf(END_TIME to time))
        }
    }

    fun trackScreenEnterEventsName(screenName: String) {
        eventsRepository.screen(name = screenName)
    }

    private fun loadData(type: HabitType) {
        viewModelScope.launch {
            habitRepository.getHabit(type).collect {
                _uiState.value = HabitDetailUiState(
                    loading = false,
                    habit = it
                )
            }
        }
    }
}
