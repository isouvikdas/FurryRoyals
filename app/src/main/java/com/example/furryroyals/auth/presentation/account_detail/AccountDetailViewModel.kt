package com.example.furryroyals.auth.presentation.account_detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.furryroyals.auth.data.networking.request.EmailRequest
import com.example.furryroyals.auth.data.networking.request.UsernameRequest
import com.example.furryroyals.auth.domain.AuthApi
import com.example.furryroyals.auth.presentation.login.AuthEventManager
import com.example.furryroyals.core.domain.util.onError
import com.example.furryroyals.core.domain.util.onSuccess
import com.example.furryroyals.core.presentation.util.Event
import com.example.furryroyals.core.domain.UserRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AccountDetailUiState(
    val username: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

data class EmailVerificationUiState(
    val email: String = "",
    val isEmailVerified: Boolean = false,
    val isOtpSent: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class AccountDetailViewModel(
    private val userRepository: UserRepository,
    private val authApi: AuthApi,
    private val authEventManager: AuthEventManager
) : ViewModel() {

    private val _accountDetailUiState = MutableStateFlow(AccountDetailUiState())
    val accountDetailUiState: StateFlow<AccountDetailUiState> =
        _accountDetailUiState.asStateFlow()

    init {
        setAccountDetail()
        observeAuthEvents()
    }

    private val _emailVerificationUiState = MutableStateFlow(EmailVerificationUiState(email = accountDetailUiState.value.email))
    val emailVerificationUiState: StateFlow<EmailVerificationUiState> =
        _emailVerificationUiState.asStateFlow()

    private val _resetKey = MutableStateFlow(0)
    val resetKey: StateFlow<Int> = _resetKey.asStateFlow()

    private val _events = Channel<Event>()
    val events = _events.receiveAsFlow()

    private fun setAccountDetail() {
        viewModelScope.launch {

            lateinit var username: String
            lateinit var email: String
            lateinit var phoneNumber: String

            userRepository.getUserEmail()?.let {
                email = it
                Log.i("toggle", "email: $email")
            }
            userRepository.getUserName()?.let {
                username = it
                Log.i("toggle", "username: $username")

            }
            userRepository.getUserPhoneNumber()?.let {
                phoneNumber = it
                Log.i("toggle", "phone number: $phoneNumber")

            }

            _accountDetailUiState.update {
                if (email.isNotEmpty()) {
                    it.copy(
                        username = username,
                        email = email,
                        phoneNumber = phoneNumber
                    )
                } else {
                    it.copy(
                        username = username,
                        phoneNumber = phoneNumber
                    )
                }
            }
        }
    }

    private fun handleInvalidToken(errorMessage: String): Boolean {
        if (!userRepository.isLoggedIn() || userRepository.getToken().isNullOrEmpty()) {
            _accountDetailUiState.update { it.copy(errorMessage = errorMessage, isLoading = false) }
            return false
        }
        return true
    }

    fun updateUsername(username: String) {
        viewModelScope.launch {
            _accountDetailUiState.update { it.copy(isLoading = true) }

            if (!handleInvalidToken("Invalid token: Please login again")) return@launch

            val token = userRepository.getToken()!!
            Log.d("updateUsername in vm", token)
            val result = authApi.updateUsername(token, UsernameRequest(username))
            result.onSuccess { apiResponse ->
                if (apiResponse.success) {
                    userRepository.saveUsername(username)
                    _accountDetailUiState.update {
                        it.copy(username = username, isLoading = false, errorMessage = null)
                    }

                    setAccountDetail()
                } else {
                    _accountDetailUiState.update {
                        it.copy(
                            errorMessage = apiResponse.message,
                            isLoading = false
                        )
                    }
                }
            }.onError { error ->
                _accountDetailUiState.update { it.copy(isLoading = false) }
                _events.send(Event.Error(error))
            }
        }
    }

    fun sendOtpToEmail(email: String) {
        viewModelScope.launch {
            _emailVerificationUiState.update {
                it.copy(isLoading = true, isOtpSent = false, isEmailVerified = false, email = "")
            }

            if (!handleInvalidToken("Invalid token: Please login again")) return@launch

            val token = userRepository.getToken()!!
            val result = authApi.sendEmailRequest(token, EmailRequest(email))
            result.onSuccess { apiResponse ->
                if (apiResponse.success) {
                    _emailVerificationUiState.update {
                        it.copy(isOtpSent = true, isLoading = false, errorMessage = null, email = email)
                    }
                } else {
                    _emailVerificationUiState.update {
                        it.copy(
                            errorMessage = apiResponse.message,
                            isLoading = false
                        )
                    }
                }
            }.onError { error ->
                _accountDetailUiState.update { it.copy(isLoading = false) }
                _events.send(Event.Error(error))
            }
        }
    }

    fun verifyEmail(otp: String) {
        viewModelScope.launch {
            _emailVerificationUiState.update { it.copy(isLoading = true) }

            if (!handleInvalidToken("Invalid token: Please login again")) return@launch

            val token = userRepository.getToken()!!
            val email = _emailVerificationUiState.value.email
            val result = authApi.verifyEmailRequest(token, EmailRequest(email, otp))
            result.onSuccess {apiResponse ->
                if (apiResponse.success) {
                    userRepository.saveUserEmail(email)
                    _emailVerificationUiState.update {
                        it.copy(
                            isEmailVerified = true,
                            isOtpSent = false,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                    setAccountDetail()
                } else {
                    _emailVerificationUiState.update {
                        it.copy(
                            errorMessage = apiResponse.message,
                            isLoading = false
                        )
                    }
                }
            }.onError { error ->
                _accountDetailUiState.update { it.copy(isLoading = false) }
                _events.send(Event.Error(error))
            }
        }
    }

    fun resetEmailVerificationUiState() {
        _emailVerificationUiState.value = EmailVerificationUiState()
    }

    private fun observeAuthEvents() {
        viewModelScope.launch {
            authEventManager.events.collect { event ->
                if (event is Event.LoggedIn) {
                    setAccountDetail()
                }
            }
        }
    }

}

