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
            val result = authRepository.loginUser(phoneNumber, password)
            _loginUiState.update {
                if (result.isSuccess) {
                    val token = result.getOrNull()?.token
                    val expirationTime = result.getOrNull()?.expirationTime
                    val userId = result.getOrNull()?.userId
                    val username = result.getOrNull()?.username
                    val email = result.getOrNull()?.email
                    if (token != null && expirationTime != null && userId != null && username != null) {
                        userRepository.saveUserData(token, expirationTime, userId, phoneNumber, username)
                        Log.d("Login", "token: $token")
                        Log.d("Login", "expirationTime: $expirationTime")
                        Log.d("Login", "userId: $userId")
                        Log.d("Login", "phoneNumber: $phoneNumber")
                        Log.d("Login", "username: $username")
                    }
                    if (email != null)  {
                        userRepository.saveUserData(email)
                        Log.d("Login", "email: $email")
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
