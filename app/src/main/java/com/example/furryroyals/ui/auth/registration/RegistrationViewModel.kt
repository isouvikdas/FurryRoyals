package com.example.furryroyals.ui.auth.registration

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

data class RegistrationUiState(
    val username: String = "",
    val phoneNumber: String = "",
    val password: String = "",
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

    private val _registrationUiState = MutableStateFlow(RegistrationUiState())
    val registrationUiState: StateFlow<RegistrationUiState> = _registrationUiState.asStateFlow()

    fun sendOtp(phoneNumber: String) {
        viewModelScope.launch {
            _registrationUiState.update { it.copy(isLoading = true) }
            val result = userRepository.sendOtp(phoneNumber)
            _registrationUiState.update {
                if (result.isSuccess) {
                    it.copy(
                        isOtpSent = true,
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

    fun verifyOtp(phoneNumber: String, otp: String) {
        viewModelScope.launch {
            _registrationUiState.update { it.copy(isLoading = true) }
            val result = userRepository.verifyOtp(phoneNumber, otp)
            _registrationUiState.update {
                if (result.isSuccess) {
                    it.copy(
                        isOtpVerified = true,
                        errorMessage = null,
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

    fun registerUser(username: String, password: String) {
        viewModelScope.launch {
            _registrationUiState.update { it.copy(isLoading = true) }
            val result = userRepository.registerUser(_registrationUiState.value.phoneNumber, username, password)
            _registrationUiState.update {
                if (result.isSuccess) {
                    it.copy(
                        isLoading = false,
                        registrationSuccess = true,
                        errorMessage = null,
                        username = username,
                        password = ""
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

