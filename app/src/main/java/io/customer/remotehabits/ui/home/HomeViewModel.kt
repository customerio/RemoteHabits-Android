package io.customer.remotehabits.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.customer.remotehabits.data.models.Habit
import io.customer.remotehabits.data.models.User
import io.customer.remotehabits.data.repositories.HabitRepository
import io.customer.remotehabits.data.repositories.UserRepository
import io.customer.remotehabits.ui.navigation.LoginDirections
import io.customer.remotehabits.ui.navigation.NavigationManager
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val user: User = User("", ""),
    val loading: Boolean = false,
    val habits: List<Habit> = emptyList()
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val habitRepository: HabitRepository,
    private val navigationManager: NavigationManager
) : ViewModel() {

    // UI state exposed to the UI
    private val _uiState = MutableStateFlow(HomeUiState(loading = true))
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            combine(
                userRepository.getUser(),
                habitRepository.getHabits()
            ) { user: User?, habits: List<Habit> ->
                HomeUiState(
                    user = user ?: User("", ""),
                    habits = habits
                )
            }.collect {
                _uiState.value = it
            }
        }
    }

    fun onStateChanged(habit: Habit, checked: Boolean) {
        viewModelScope.launch {
            habitRepository.updateHabit(habit.copy(status = checked))
        }
    }

    fun logout(user: User, context: Context) {
        viewModelScope.launch {
            habitRepository.reset(context)
            userRepository.deleteUser(user)
            navigationManager.navigate(LoginDirections.login)
        }
    }
}
