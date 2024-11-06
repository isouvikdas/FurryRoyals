package com.example.furryroyals.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface AuthApiService {

    @POST("auth/user/send-otp")
    suspend fun sendOtp(@Body phoneRequest: PhoneRequest): Response<ApiResponse<Any>>

    @POST("auth/user/verify-otp")
    suspend fun verifyOtp(@Body otpRequest: OtpRequest): Response<ApiResponse<Any>>

    @POST("auth/user/signup")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): Response<ApiResponse<UserResponse>>

    @POST("auth/user/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<ApiResponse<UserResponse>>

    @PUT("auth/user/set-email/send-otp")
    suspend fun sendEmailRequest(
        @Header("Authorization") token: String,
        @Body emailRequest: EmailRequest
    ): Response<ApiResponse<Any>>

    @PUT("auth/user/set-email")
    suspend fun verifyEmailRequest(
        @Header("Authorization") token: String,
        @Body emailRequest: EmailRequest
    ): Response<ApiResponse<Any>>

    @PUT("auth/user/update-usernamez")
    suspend fun updateUsername(
        @Header("Authorization") token: String,
        @Body usernameRequest: UsernameRequest
    ): Response<ApiResponse<Any>>

}

data class UsernameRequest(val username: String)
data class EmailRequest(val email: String, val otp: String) {
    constructor(email: String) : this(email, otp = "")
}

data class UserResponse(
    val userId: String, val username: String, val email: String,
    val phoneNumber: String, val token: String, val expirationTime: Long
)

data class ApiResponse<T>(val message: String, val success: Boolean, val data: T)
data class LoginRequest(val phoneNumber: String, val password: String)
data class PhoneRequest(val phoneNumber: String)
data class OtpRequest(val phoneNumber: String, val otp: String)
data class RegisterRequest(val phoneNumber: String, val username: String, val password: String)
