package io.customer.remotehabits.ui.login

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.customer.remotehabits.data.repositories.UserRepository
import io.customer.remotehabits.ui.navigation.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    fun getDestination(): String {
        val isLoggedIn = runBlocking(Dispatchers.IO) {
            userRepository.isLoggedIn()
        }
        return if (isLoggedIn) {
            Screen.Dashboard.route
        } else {
            Screen.Login.route
        }
    }
}
