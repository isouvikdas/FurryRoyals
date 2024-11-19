package com.example.furryroyals.auth.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.furryroyals.auth.data.networking.AuthApi
import com.example.furryroyals.auth.data.networking.request.PhoneRequest
import com.example.furryroyals.auth.data.networking.request.VerifyAndLoginRequest
import com.example.furryroyals.core.domain.util.onError
import com.example.furryroyals.core.domain.util.onSuccess
import com.example.furryroyals.core.presentation.util.Event
import com.example.furryroyals.repository.UserRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoginUiState(
    val phoneNumber: String = "",
    val otp: String = "",
    val isOtpSent: Boolean = false,
    val registrationSuccess: Boolean = false,
    val isOtpVerified: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)


class LoginViewModel(
    private val userRepository: UserRepository,
    private val authApi: AuthApi,
    private val authEventManager: AuthEventManager
) : ViewModel() {

    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState: StateFlow<LoginUiState> = _loginUiState.asStateFlow()


    private val _events = Channel<Event>()
    val events = _events.receiveAsFlow()

    fun resetState() {
        _loginUiState.value = LoginUiState()
        Log.i("toggle", "isOtpSent: ${loginUiState.value.isOtpSent}")

    }

    fun toggleOtpSentState() {
        viewModelScope.launch {
            _loginUiState.update { it.copy(isOtpSent = !_loginUiState.value.isOtpSent) }
            Log.i("toggle", "isOtpSent: ${loginUiState.value.isOtpSent}")

        }
    }

    fun sendOtp(phoneNumber: String) {
        viewModelScope.launch {
            _loginUiState.update { it.copy(isLoading = true) }
            val result = authApi.sendOtp(PhoneRequest("+91$phoneNumber"))
            result.onSuccess { apiResponse ->
                _loginUiState.update {
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
                _loginUiState.update { it.copy(isLoading = false, errorMessage = error.toString()) }
                _events.send(Event.Error(error))

            }
        }
    }

    fun verifyOtp(phoneNumber: String, otp: String) {
        viewModelScope.launch {
            _loginUiState.update { it.copy(isLoading = true) }
            val result = authApi.verifyOtp(VerifyAndLoginRequest("+91$phoneNumber", otp))
            result.onSuccess { apiResponse ->
                _loginUiState.update {
                    if (apiResponse.success) {
                        apiResponse.data?.let {userResponse ->
                            val token = userResponse.token
                            val expirationTime = userResponse.expirationTime
                            val userId = userResponse.userId
                            val number = userResponse.phoneNumber
                            val username = userResponse.username
                            Log.i("toggle", "token: $token")
                            Log.i("toggle", "expirationTime: $expirationTime")
                            Log.i("toggle", "userId: $userId")
                            Log.i("toggle", "phoneNUmber: $number")
                            Log.i("toggle", "username: $username")
                            userRepository.saveUserData(
                                token = userResponse.token,
                                expirationTime = userResponse.expirationTime,
                                userId = userResponse.userId,
                                phoneNumber = phoneNumber,
                                username = userResponse.username
                            )
                        }
                        authEventManager.sendEvent(Event.LoggedIn)
                        it.copy(
                            errorMessage = null,
                            isOtpVerified = true,
                            isLoading = false,
                            registrationSuccess = true
                        )

                    } else {
                        it.copy(
                            errorMessage = apiResponse.message,
                            isLoading = false
                        )
                    }
                }
            }.onError { error ->
                _loginUiState.update { it.copy(isLoading = false, errorMessage = error.toString()) }
                _events.send(Event.Error(error))

            }
        }
    }
}

