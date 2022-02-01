package io.customer.remotehabits.ui.login

import android.util.Patterns
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.customer.remotehabits.R
import io.customer.remotehabits.data.models.User
import io.customer.remotehabits.data.repositories.UserRepository
import io.customer.remotehabits.ui.navigation.LoginDirections
import io.customer.remotehabits.ui.navigation.NavigationManager
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
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
    private val navigationManager: NavigationManager
) : ViewModel() {

    // UI state exposed to the UI
    private val _uiState = MutableStateFlow(LoginUiState(loading = true))
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun login(email: String, name: String, isGuest: Boolean = false) {
        if (isGuest) {
            viewModelScope.launch {
                userRepository.login(email = email, name = name, isGuest = isGuest)
            }
        } else if (name.isEmpty()) {
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
            viewModelScope.launch {
                userRepository.login(email = email, name = name, isGuest = isGuest)
            }
        }
    }

    fun loginAsGuest() {
        val uuid = UUID.randomUUID().toString()
        login(
            email = uuid,
            name = "Guest",
            isGuest = true
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
                if (user != null) {
                    navigationManager.navigate(LoginDirections.dashboard)
                }
            }
        }
    }
}
