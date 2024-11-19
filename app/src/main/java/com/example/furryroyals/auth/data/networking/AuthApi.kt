package com.example.furryroyals.auth.data.networking

import com.example.furryroyals.auth.data.networking.request.PhoneRequest
import com.example.furryroyals.auth.data.networking.request.VerifyAndLoginRequest
import com.example.furryroyals.auth.data.networking.response.UserResponse
import com.example.furryroyals.core.data.networking.constructUrl
import com.example.furryroyals.core.data.networking.safeCall
import com.example.furryroyals.core.domain.util.NetworkError
import com.example.furryroyals.core.domain.util.Result
import com.example.furryroyals.core.response.ApiResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody

class AuthApi(
    private val httpClient: HttpClient
) {

    suspend fun sendOtp(phoneRequest: PhoneRequest):
            Result<ApiResponse<Unit>, NetworkError> {
        return safeCall {
            httpClient.post(
                urlString = constructUrl("auth/simple/user/send-otp")
            ) {
                setBody(phoneRequest)
            }
        }
    }

    suspend fun verifyOtp(verifyAndLoginRequest: VerifyAndLoginRequest):
            Result<ApiResponse<UserResponse>, NetworkError> {
        return safeCall {
            httpClient.post(
                urlString = constructUrl("auth/simple/user/verify-otp")
            ) {
                setBody(verifyAndLoginRequest)
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





