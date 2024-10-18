package com.example.furryroyals.repository

import com.example.furryroyals.api.ApiResponse
import com.example.furryroyals.api.AuthApiService
import com.example.furryroyals.api.OtpRequest
import com.example.furryroyals.api.PhoneRequest
import com.example.furryroyals.api.RegisterRequest
import com.example.furryroyals.api.UserResponse
import com.example.furryroyals.model.User
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val authApiService: AuthApiService
) {

    suspend fun sendOtp(phoneNumber: String) : Result<Unit> {
        return try {
            authApiService.sendOtp(PhoneRequest(phoneNumber))
            Result.success(Unit)
        } catch (e : RuntimeException) {
            Result.failure(e)
        }
    }

    suspend fun verifyOtp(phoneNumber: String, otp: String) : Result<Boolean> {
        return try {
            val response = authApiService.verifyOtp(OtpRequest(phoneNumber, otp))
            if (response.isSuccessful) {
                val apiResponse : ApiResponse? = response.body()
                if (apiResponse != null && apiResponse.success) {
                    Result.success(true)
                } else {
                    Result.failure(RuntimeException(apiResponse?.message ?: "Unknown error"))
                }
            } else {
                Result.failure(RuntimeException("Failed to verify OTP: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun registerUser(user: User): Result<UserResponse> {
        return try {
            val response = authApiService.registerUser(RegisterRequest(user))
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(RuntimeException("Empty response body"))
            } else {
                Result.failure(RuntimeException("Failed to register: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}