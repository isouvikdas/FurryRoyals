package com.example.furryroyals.ui.auth.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.furryroyals.repository.AuthRepository
import com.example.furryroyals.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginUiState(
    val phoneNumber: String = "",
    val password: String = "",
    val loginSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState: StateFlow<LoginUiState> = _loginUiState.asStateFlow()


    fun login(phoneNumber: String, password: String) {
        viewModelScope.launch {
            _loginUiState.update { it.copy(isLoading = true) }
            val result = userRepository.loginUser(phoneNumber, password)
            _loginUiState.update {
                if (result.isSuccess) {
                    val token = result.getOrNull()?.token
                    val expirationTime = result.getOrNull()?.expirationTime
                    if (token != null && expirationTime != null) {
                        authRepository.saveToken(token, expirationTime)
                        Log.d("jwt", "token: $token")
                        Log.d("jwt", "expirationTime: $expirationTime")
                    }
                    it.copy(
                        loginSuccess = true,
                        errorMessage = null,
                        phoneNumber = phoneNumber,
                        isLoading = false
                    )
                } else {
                    it.copy(
                        isLoading = false,
                        errorMessage = result.exceptionOrNull()?.message
                    )
                }
            }
        }
    }
}
