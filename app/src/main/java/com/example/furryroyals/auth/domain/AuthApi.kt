package com.example.furryroyals.auth.domain

import com.example.furryroyals.auth.data.networking.request.EmailRequest
import com.example.furryroyals.auth.data.networking.request.LoginRequest
import com.example.furryroyals.auth.data.networking.request.UsernameRequest
import com.example.furryroyals.auth.data.networking.response.UserResponse
import com.example.furryroyals.core.domain.util.NetworkError
import com.example.furryroyals.core.domain.util.Result
import com.example.furryroyals.core.response.ApiResponse

interface AuthApi {
    suspend fun sendOtp(loginRequest: LoginRequest):
            Result<ApiResponse<Unit>, NetworkError>

    suspend fun verifyOtp(loginRequest: LoginRequest):
            Result<ApiResponse<UserResponse>, NetworkError>

    suspend fun sendEmailRequest(
        token: String,
        emailRequest: EmailRequest
    ): Result<ApiResponse<Unit>, NetworkError>

    suspend fun verifyEmailRequest(
        token: String,
        emailRequest: EmailRequest
    ): Result<ApiResponse<Unit>, NetworkError>

    suspend fun updateUsername(
        token: String,
        usernameRequest: UsernameRequest
    ): Result<ApiResponse<Unit>, NetworkError>
}
