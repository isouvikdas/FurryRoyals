package com.example.furryroyals.auth.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.furryroyals.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RegistrationUiState(
    val username: String = "",
    val phoneNumber: String = "",
    val otp: String = "",
    val isOtpSent: Boolean = false,
    val isOtpVerified: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)


@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegistrationUiState())
    val uiState: StateFlow<RegistrationUiState> = _uiState

    fun updateUserInput(username: String, phoneNumber: String) {
        _uiState.value = _uiState
            .value.copy(
                username = username,
                phoneNumber = phoneNumber
            )
    }

    fun sendOtp() {
        viewModelScope.launch {
            // Trigger API call for sending OTP
            // Update isOtpSent and isLoading accordingly
        }
    }

    fun verifyOtp(otp: String) {
        viewModelScope.launch {
            // Trigger OTP verification and update isOtpVerified
        }
    }
}
