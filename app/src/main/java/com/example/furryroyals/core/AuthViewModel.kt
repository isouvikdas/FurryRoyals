package com.example.furryroyals.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.furryroyals.auth.presentation.login.AuthEventManager
import com.example.furryroyals.core.presentation.util.Event
import com.example.furryroyals.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val userRepository: UserRepository,
    private val authEventManager: AuthEventManager
) : ViewModel() {

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _resetKey = MutableStateFlow(0)
    val resetKey: StateFlow<Int> = _resetKey.asStateFlow()

    init {
        checkTokenValidity()
        observeAuthEvents()
    }

    private fun checkTokenValidity() {
        viewModelScope.launch {
            _isLoggedIn.value = userRepository.isLoggedIn()
        }
    }

    fun logout() {
        viewModelScope.launch {
            val result = userRepository.clearUserData()
            if (result.isSuccess) {
                _isLoggedIn.value = false
                _resetKey.value += 1
            }
        }
    }

    private fun observeAuthEvents() {
        viewModelScope.launch {
            authEventManager.events.collect { event ->
                if (event is Event.LoggedIn) {
                    checkTokenValidity()
                }
            }
        }
    }
}