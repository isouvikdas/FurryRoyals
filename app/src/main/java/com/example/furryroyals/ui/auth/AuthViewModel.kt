package com.example.furryroyals.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.furryroyals.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _resetKey = MutableStateFlow(0)
    val resetKey: StateFlow<Int> = _resetKey.asStateFlow()

    init {
        checkTokenValidity()
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
}