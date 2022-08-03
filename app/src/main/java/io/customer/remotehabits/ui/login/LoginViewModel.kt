package io.customer.remotehabits.ui.login

import android.app.Application
import android.util.Patterns
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.customer.remotehabits.R
import io.customer.remotehabits.data.models.User
import io.customer.remotehabits.data.repositories.EventsRepository
import io.customer.remotehabits.data.repositories.PreferenceRepository
import io.customer.remotehabits.data.repositories.UserRepository
import io.customer.remotehabits.utils.AnalyticsConstants.EMAIL
import io.customer.remotehabits.utils.AnalyticsConstants.IS_GUEST
import io.customer.remotehabits.utils.AnalyticsConstants.LOGIN_ATTEMPT
import io.customer.remotehabits.utils.AnalyticsConstants.NAME
import io.customer.sdk.CustomerIO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

/**
 * UI state for the Login screen
 */
data class LoginUiState(
    @StringRes val emailError: Int? = null,
    @StringRes val nameError: Int? = null,
    val loading: Boolean = false,
    val user: User? = null
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val eventsRepository: EventsRepository,
    private val preferenceRepository: PreferenceRepository
) : ViewModel() {

    // UI state exposed to the UI
    private val _uiState = MutableStateFlow(LoginUiState(loading = true))
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _trackApiUrl: MutableStateFlow<String?> = MutableStateFlow("")
    val trackApiUrl = _trackApiUrl

    fun loginUser(email: String, name: String, onLoginSuccess: () -> Unit) {
        eventsRepository.track(
            name = LOGIN_ATTEMPT,
            attributes = mapOf(NAME to name, EMAIL to email)
        )
        if (name.isEmpty()) {
            _uiState.update {
                it.copy(
                    nameError = R.string.invalid_name,
                    emailError = null
                )
            }
        } else if (email.isEmpty() || Patterns.EMAIL_ADDRESS.matcher(email).matches().not()) {
            _uiState.update {
                it.copy(
                    emailError = R.string.invalid_email,
                    nameError = null
                )
            }
        } else {
            login(email = email, name = name, onLoginSuccess = onLoginSuccess)
        }
    }

    fun trackScreenName(name: String) {
        viewModelScope.launch {
            eventsRepository.screen(name = name)
        }
    }

    private fun login(
        email: String,
        name: String,
        isGuest: Boolean = false,
        onLoginSuccess: () -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.login(email = email, name = name, isGuest = isGuest)
            eventsRepository.identify(
                identifier = email,
                attributes = mapOf(NAME to name, IS_GUEST to isGuest)
            )
            withContext(Dispatchers.Main) {
                onLoginSuccess.invoke()
            }
        }
    }

    fun loginAsGuest(onLoginSuccess: () -> Unit) {
        val uuid = UUID.randomUUID().toString()
        login(
            email = uuid,
            name = "Guest",
            isGuest = true,
            onLoginSuccess = onLoginSuccess
        )
    }

    init {
        loadData()
    }

    private fun loadData() {
        _uiState.update { it.copy(loading = true) }

        viewModelScope.launch {
            userRepository.getUser().collect { user ->
                _uiState.update {
                    it.copy(
                        loading = false,
                        emailError = null,
                        nameError = null,
                        user = user
                    )
                }
            }
        }

        viewModelScope.launch {
            preferenceRepository.getTrackApiUrl().collect {
                _trackApiUrl.emit(it)
            }
        }
    }

    fun updateTrackApiUrl(url: String, application: Application, onComplete: () -> Unit) {
        viewModelScope.launch {
            preferenceRepository.saveTrackApiUrl(url)

            val workspace = preferenceRepository.getWorkspaceCredentials().first()

            CustomerIO.Builder(
                siteId = workspace.siteId,
                apiKey = workspace.apiKey,
                appContext = application
            ).setTrackingApiURL(trackingApiUrl = url)
                .build()

            onComplete.invoke()
        }
    }
}
