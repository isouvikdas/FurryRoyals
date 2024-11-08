package com.example.furryroyals.ui.profile.accountDetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.furryroyals.api.AuthApiService
import com.example.furryroyals.repository.AuthRepository
import com.example.furryroyals.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

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

@HiltViewModel
class AccountDetailViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _emailVerificationUiState = MutableStateFlow(EmailVerificationUiState())
    val emailVerificationUiState: StateFlow<EmailVerificationUiState> = _emailVerificationUiState.asStateFlow()


    private val _accountDetailUiState = MutableStateFlow(AccountDetailUiState())
    val accountDetailUiState: StateFlow<AccountDetailUiState> = _accountDetailUiState.asStateFlow()

    private val _resetKey = MutableStateFlow(0)
    val resetKey : StateFlow<Int> = _resetKey.asStateFlow()


    init {
        setAccountDetail()
    }

    private fun setAccountDetail() {
        viewModelScope.launch {

            lateinit var username: String
            lateinit var email: String
            lateinit var phoneNumber: String

            userRepository.getUserEmail()?.let {
                email = it
            }
            userRepository.getUserName()?.let {
                username = it
            }
            userRepository.getUserPhoneNumber()?.let {
                phoneNumber = it
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
            val result = authRepository.updateUsername(token, username)
            if (result.isSuccess) {
                userRepository.saveUsername(username)
                _accountDetailUiState.update {
                    it.copy(username = username, isLoading = false, errorMessage = null)
                }

                setAccountDetail()
            } else {
                _accountDetailUiState.update {
                    it.copy(
                        errorMessage = result.exceptionOrNull()?.message
                            ?: "Failed to update username",
                        isLoading = false
                    )
                }
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
            val result = authRepository.sendOtpToEmail(token, email)
            if (result.isSuccess) {
                _emailVerificationUiState.update {
                    it.copy(isOtpSent = true, isLoading = false, errorMessage = null, email = email)
                }
            } else {
                _emailVerificationUiState.update {
                    it.copy(
                        errorMessage = result.exceptionOrNull()?.message ?: "Failed to send otp",
                        isLoading = false
                    )
                }
            }
        }
    }

    fun verifyEmail(otp: String) {
        viewModelScope.launch {
            _emailVerificationUiState.update { it.copy(isLoading = true) }

            if (!handleInvalidToken("Invalid token: Please login again")) return@launch

            val token = userRepository.getToken()!!
            val email = _emailVerificationUiState.value.email
            val result = authRepository.verifyEmail(token, email, otp)
            if (result.isSuccess) {
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
                        errorMessage = result.exceptionOrNull()?.message
                            ?: "Failed to verify email",
                        isLoading = false
                    )
                }
            }
        }
    }

    fun resetEmailVerificationUiState() {
        _emailVerificationUiState.value = EmailVerificationUiState()
    }

}

