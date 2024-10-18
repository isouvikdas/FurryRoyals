package com.example.furryroyals.api

import com.example.furryroyals.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("auth/user/send-otp")
    suspend fun sendOtp(@Body phoneRequest: PhoneRequest)

    @POST("auth/user/verify-otp")
    suspend fun verifyOtp(@Body otpRequest: OtpRequest) : Response<ApiResponse>

    @POST("auth/user/signup")
    suspend fun registerUser(@Body registerRequest: RegisterRequest) : Response<UserResponse>

}

data class PhoneRequest(val phoneNumber: String)
data class OtpRequest(val phoneNumber: String, val otp: String)
data class RegisterRequest(val user: User)
data class UserResponse(val userId: String, val username: String, val email: String,
                            val phoneNumber: String, val token: String)
data class ApiResponse(val message: String, val success: Boolean, val data: Any)
