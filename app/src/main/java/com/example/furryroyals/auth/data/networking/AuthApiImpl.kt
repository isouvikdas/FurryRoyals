package com.example.furryroyals.auth.data.networking

import com.example.furryroyals.auth.data.networking.request.EmailRequest
import com.example.furryroyals.auth.data.networking.request.UsernameRequest
import com.example.furryroyals.auth.data.networking.request.LoginRequest
import com.example.furryroyals.auth.data.networking.response.UserResponse
import com.example.furryroyals.auth.domain.AuthApi
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

class AuthApiImpl(
    private val httpClient: HttpClient
): AuthApi {

    override suspend fun sendOtp(loginRequest: LoginRequest):
            Result<ApiResponse<Unit>, NetworkError> {
        return safeCall {
            httpClient.post(
                urlString = constructUrl("auth/simple/user/send-otp")
            ) {
                setBody(loginRequest)
            }
        }
    }

    override suspend fun verifyOtp(loginRequest: LoginRequest):
            Result<ApiResponse<UserResponse>, NetworkError> {
        return safeCall {
            httpClient.post(
                urlString = constructUrl("auth/simple/user/verify-otp")
            ) {
                setBody(loginRequest)
            }
        }
    }


    override suspend fun sendEmailRequest(
        token: String,
        emailRequest: EmailRequest
    ): Result<ApiResponse<Unit>, NetworkError> {
        return safeCall {
            httpClient.put(
                urlString = constructUrl("auth/user/set-email/send-otp")
            ) {
                header("Authorization", token)
                setBody(emailRequest)
            }
        }
    }

    override suspend fun verifyEmailRequest(
        token: String,
        emailRequest: EmailRequest
    ): Result<ApiResponse<Unit>, NetworkError> {
        return safeCall {
            httpClient.put(
                urlString = constructUrl("auth/user/set-email")
            ) {
                header("Authorization", token)
                setBody(emailRequest)
            }
        }
    }

    override suspend fun updateUsername(
        token: String,
        usernameRequest: UsernameRequest
    ): Result<ApiResponse<Unit>, NetworkError> {
        return safeCall {
            httpClient.put(
                urlString = constructUrl("auth/user/update-username")
            ) {
                header("Authorization", token)
                setBody(usernameRequest)
            }
        }
    }

}







