package com.example.furryroyals.ui.auth.registration

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.furryroyals.model.User
import com.example.furryroyals.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RegistrationUiState(
    val username: String = "",
    val phoneNumber: String = "",
    val otp: String = "",
    val isOtpSent: Boolean = false,
    val registrationSuccess : Boolean = false,
    val isOtpVerified: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)


@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    var registrationUiState by mutableStateOf(RegistrationUiState())

    fun sendOtp(phoneNumber: String) {
        viewModelScope.launch {
            registrationUiState = registrationUiState.copy(isLoading = true)
            val result = userRepository.sendOtp(phoneNumber)
            registrationUiState = if (result.isSuccess) {
                registrationUiState.copy(
                    isOtpSent = true,
                    errorMessage = null,
                    phoneNumber = phoneNumber,
                    isLoading = false
                )
            } else {
                registrationUiState.copy(
                    isLoading = false,
                    errorMessage = result.exceptionOrNull()?.message
                )
            }
        }
    }

    fun verifyOtp(phoneNumber: String, otp: String) {
        viewModelScope.launch {
            registrationUiState = registrationUiState.copy(isLoading = true)
            val result = userRepository.verifyOtp(phoneNumber, otp)
            registrationUiState = if (result.isSuccess) {
                registrationUiState.copy(
                    isOtpVerified = true,
                    errorMessage = null,
                    isLoading = false
                )
            } else {
                registrationUiState.copy(
                    isLoading = false,
                    errorMessage = result.exceptionOrNull()?.message
                )
            }
        }
    }

    fun registerUser(username: String, password: String) {
        viewModelScope.launch {
            registrationUiState = registrationUiState.copy(isLoading = true)
            val result = userRepository.registerUser(registrationUiState.phoneNumber, username, password)
            registrationUiState = if (result.isSuccess) {
                registrationUiState.copy(
                    isLoading = false,
                    registrationSuccess = true,
                    errorMessage = null,
                    username = username,
                )
            } else {
                registrationUiState.copy(
                    isLoading = false,
                    errorMessage = result.exceptionOrNull()?.message
                )
            }
        }
    }
}
