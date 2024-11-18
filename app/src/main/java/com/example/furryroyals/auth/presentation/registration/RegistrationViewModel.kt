package com.example.furryroyals.auth.presentation.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.furryroyals.auth.data.networking.AuthApi
import com.example.furryroyals.auth.data.networking.OtpRequest
import com.example.furryroyals.auth.data.networking.PhoneRequest
import com.example.furryroyals.core.domain.util.onError
import com.example.furryroyals.core.domain.util.onSuccess
import com.example.furryroyals.repository.UserRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class RegistrationUiState(
    val phoneNumber: String = "",
    val otp: String = "",
    val isOtpSent: Boolean = false,
    val registrationSuccess: Boolean = false,
    val isOtpVerified: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)


class RegistrationViewModel(
    private val userRepository: UserRepository,
    private val authApi: AuthApi
) : ViewModel() {

    private val _registrationUiState = MutableStateFlow(RegistrationUiState())
    val registrationUiState: StateFlow<RegistrationUiState> = _registrationUiState.asStateFlow()

    private val _events = Channel<Event>()
    val events = _events.receiveAsFlow()

    fun sendOtp(phoneNumber: String) {
        viewModelScope.launch {
            _registrationUiState.update { it.copy(isLoading = true) }
            val result = authApi.sendOtp(PhoneRequest("+91$phoneNumber"))
            result.onSuccess { apiResponse ->
                _registrationUiState.update {
                    if (apiResponse.success) {
                        it.copy(
                            phoneNumber = phoneNumber,
                            isOtpSent = true,
                            errorMessage = null,
                            isLoading = false
                        )
                    } else {
                        it.copy(
                            errorMessage = apiResponse.message,
                            isLoading = false
                        )
                    }
                }
            }.onError { error ->
                _registrationUiState.update { it.copy(isLoading = false) }
                _events.send(Event.Error(error))

            }
        }
    }

    fun verifyOtp(phoneNumber: String, otp: String) {
        viewModelScope.launch {
            _registrationUiState.update { it.copy(isLoading = true) }
            val result = authApi.verifyOtp(OtpRequest("+91$phoneNumber", otp))
            result.onSuccess { apiResponse ->
                _registrationUiState.update {
                    if (apiResponse.success) {
                        val userResponse = apiResponse.data
                        userRepository.saveUserData(
                            token = userResponse.token,
                            expirationTime = userResponse.expirationTime,
                            userId = userResponse.userId,
                            phoneNumber = phoneNumber,
                            username = userResponse.username
                        )
                        it.copy(
                            isOtpVerified = true,
                            isLoading = false
                        )
                    } else {
                        it.copy(
                            errorMessage = apiResponse.message,
                            isLoading = false
                        )
                    }
                }
            }.onError { error ->
                _registrationUiState.update { it.copy(isLoading = false) }
                _events.send(Event.Error(error))

            }
        }
    }
}

