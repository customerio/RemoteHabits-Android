package io.customer.remotehabits.ui.home

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import io.customer.remotehabits.data.models.Habit
import io.customer.remotehabits.data.models.User
import io.customer.remotehabits.data.models.Workspace
import io.customer.remotehabits.data.repositories.EventsRepository
import io.customer.remotehabits.data.repositories.HabitRepository
import io.customer.remotehabits.data.repositories.PreferenceRepository
import io.customer.remotehabits.data.repositories.UserRepository
import io.customer.remotehabits.utils.AnalyticsConstants.LOGOUT
import io.customer.remotehabits.utils.AnalyticsConstants.STATUS
import io.customer.remotehabits.utils.Logger
import io.customer.sdk.CustomerIO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
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
    private val eventsRepository: EventsRepository,
    private val preferenceRepository: PreferenceRepository,
    private val logger: Logger
) : ViewModel() {

    // UI state exposed to the UI
    private val _uiState = MutableStateFlow(HomeUiState(loading = true))
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _workspaceCredentials = MutableStateFlow(Workspace(siteId = "", apiKey = ""))
    val workspaceCredentials = _workspaceCredentials

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

        viewModelScope.launch {
            preferenceRepository.getWorkspaceCredentials().collect {
                _workspaceCredentials.value = it
            }
        }
    }

    fun trackScreenEnterEventsName(screenName: String) {
        eventsRepository.screen(name = screenName)
        registerToken()
    }

    // It's always best to fetch the latest device token from FCM before registering with CIO.
    private fun registerToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(
            OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    logger.e("Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }
                // Get new FCM registration token
                val token = task.result
                logger.d("Token: $token")
                eventsRepository.registerDeviceToken(token)
            }
        )
    }

    fun onStateChanged(habit: Habit, checked: Boolean) {
        viewModelScope.launch {
            habitRepository.updateHabit(habit.copy(status = checked))
            eventsRepository.track(name = habit.name, attributes = mapOf(STATUS to checked))
        }
    }

    fun changeWorkspace(
        user: User,
        siteId: String,
        apiKey: String,
        appContext: Application,
        onWorkspaceChanged: () -> Unit
    ) {
        viewModelScope.launch {
            preferenceRepository.saveWorkspaceCredentials(
                siteId = siteId,
                apiKey = apiKey
            )
        }
        // Register the `CustomerIO` instance with updated workspace credentials `siteId` and `apiKey`
        CustomerIO.Builder(
            siteId = siteId,
            apiKey = apiKey,
            appContext = appContext
        ).build()
        logout(user = user, context = appContext, onLogout = onWorkspaceChanged)
    }

    fun logout(user: User, context: Context, onLogout: () -> Unit) {
        viewModelScope.launch {
            habitRepository.reset(context)
            userRepository.deleteUser(user)
            eventsRepository.track(name = LOGOUT)
            eventsRepository.clearIdentify()
            onLogout.invoke()
        }
    }
}
