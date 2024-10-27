package com.example.furryroyals.ui.auth.login

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    val loginSuccess : Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState : StateFlow<LoginUiState> = _loginUiState.asStateFlow()


    fun login(phoneNumber: String, password: String) {
        viewModelScope.launch {
            _loginUiState.update { it.copy(isLoading = true) }
            val result = userRepository.loginUser(phoneNumber,password)
            _loginUiState.update {
                if (result.isSuccess) {
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
