package com.example.furryroyals.ui.auth.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.furryroyals.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginUiState(
    val phoneNumber: String = "",
    val password: String = "",
    val loginSuccess : Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    var loginUiState by mutableStateOf(LoginUiState())


    fun login(phoneNumber: String, password: String) {
        viewModelScope.launch {
            loginUiState = loginUiState.copy(isLoading = true)
            Log.d("LoginUser", "phoneNumber in viewmodel: $phoneNumber")
            Log.d("LoginUser", "password in viewmodel: $password")
            val result = userRepository.loginUser(phoneNumber,password)
            loginUiState = if (result.isSuccess) {
                loginUiState.copy(
                    loginSuccess = true,
                    errorMessage = null,
                    phoneNumber = phoneNumber,
                    isLoading = false
                )
            } else {
                loginUiState.copy(
                    isLoading = false,
                    errorMessage = result.exceptionOrNull()?.message
                )
            }
        }
    }

}
