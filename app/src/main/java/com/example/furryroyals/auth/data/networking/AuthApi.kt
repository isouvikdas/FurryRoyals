package com.example.furryroyals.auth.data.networking

import com.example.furryroyals.core.data.networking.constructUrl
import com.example.furryroyals.core.data.networking.safeCall
import com.example.furryroyals.core.domain.util.NetworkError
import io.ktor.client.HttpClient
import com.example.furryroyals.core.domain.util.Result
import com.example.furryroyals.core.response.ApiResponse
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url

class AuthApi(
    private val httpClient: HttpClient
) {

    suspend fun sendOtp(phoneRequest: PhoneRequest): Result<ApiResponse<Any>, NetworkError> {
        return safeCall {
            httpClient.post {
                constructUrl("auth/user/send-otp")
                setBody(phoneRequest)
            }
        }
    }

    suspend fun verifyOtp(otpRequest: OtpRequest): Result<ApiResponse<UserResponse>, NetworkError> {
        return safeCall {
            httpClient.post {
                constructUrl("auth/user/verify-otp")
                setBody(otpRequest)
            }
        }
    }

    suspend fun registerUser(registerRequest: RegisterRequest): Result<ApiResponse<UserResponse>, NetworkError> {
        return safeCall {
            httpClient.post {
                constructUrl("auth/user/signup")
                setBody(registerRequest)
            }
        }
    }

    suspend fun loginUser(loginRequest: LoginRequest): Result<ApiResponse<UserResponse>, NetworkError> {
        return safeCall {
            httpClient.post {
                constructUrl("auth/user/login")
                setBody(loginRequest)
            }
        }
    }

    suspend fun sendEmailRequest(
        token: String,
        emailRequest: EmailRequest
    ): Result<ApiResponse<Any>, NetworkError> {
        return safeCall {
            httpClient.put {
                constructUrl("auth/user/set-email/send-otp")
                header("Authorization", token)
                setBody(emailRequest)
            }
        }
    }

    suspend fun verifyEmailRequest(
        token: String,
        emailRequest: EmailRequest
    ): Result<ApiResponse<Any>, NetworkError> {
        return safeCall {
            httpClient.put {
                constructUrl("auth/user/set-email")
                header("Authorization", token)
                setBody(emailRequest)
            }
        }
    }

    suspend fun updateUsername(
        token: String,
        usernameRequest: UsernameRequest
    ): Result<ApiResponse<Any>, NetworkError> {
        return safeCall {
            httpClient.put {
                constructUrl("auth/user/update-username")
                header("Authorization", token)
                setBody(usernameRequest)
            }
        }
    }

}

data class UsernameRequest(val username: String)
data class EmailRequest(val email: String, val otp: String) {
    constructor(email: String) : this(email, otp = "")
}

data class UserResponse(
    val userId: String, val username: String? = "", val email: String,
    val phoneNumber: String, val token: String, val expirationTime: Long
)

data class LoginRequest(val phoneNumber: String, val password: String)
data class PhoneRequest(val phoneNumber: String)
data class OtpRequest(val phoneNumber: String, val otp: String)
data class RegisterRequest(val phoneNumber: String, val username: String, val password: String)
