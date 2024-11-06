package com.example.furryroyals.repository

import android.util.Log
import com.example.furryroyals.api.AuthApiService
import com.example.furryroyals.api.EmailRequest
import com.example.furryroyals.api.LoginRequest
import com.example.furryroyals.api.OtpRequest
import com.example.furryroyals.api.PhoneRequest
import com.example.furryroyals.api.RegisterRequest
import com.example.furryroyals.api.UserResponse
import com.example.furryroyals.api.UsernameRequest
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authApiService: AuthApiService,
) {

    suspend fun sendOtp(phoneNumber: String): Result<Boolean> {
        return try {
            Log.d("SendOtp", "Sending OTP to phone number: +91$phoneNumber")
            val response = authApiService.sendOtp(PhoneRequest("+91$phoneNumber"))

            if (response.isSuccessful) {
                val apiResponse = response.body()
                if (apiResponse?.success == true) {
                    Log.d("SendOtp", "OTP sent successfully to: +91$phoneNumber")
                    Result.success(true)
                } else {
                    Log.e(
                        "SendOtp",
                        "OTP sending failed: ${apiResponse?.message ?: "Unknown error"}"
                    )
                    Result.failure(Exception("OTP sending failed: ${apiResponse?.message ?: "Unknown error"}"))
                }
            } else {
                Log.e("SendOtp", "HTTP error: ${response.code()} - ${response.message()}")
                Result.failure(Exception("HTTP error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Log.e("SendOtp", "OTP sending exception: ${e.localizedMessage}", e)
            Result.failure(e)
        }
    }


    suspend fun verifyOtp(phoneNumber: String, otp: String): Result<Boolean> {
        return try {
            Log.d("VerifyOtp", "Verifying OTP for phone number: +91$phoneNumber with OTP: $otp")
            val response = authApiService.verifyOtp(OtpRequest("+91$phoneNumber", otp))

            if (response.isSuccessful) {
                val apiResponse = response.body()
                if (apiResponse?.success == true) {
                    Log.d("VerifyOtp", "OTP verified successfully for: +91$phoneNumber")
                    Result.success(true)
                } else {
                    Log.e(
                        "VerifyOtp",
                        "OTP verification failed: ${apiResponse?.message ?: "Unknown error"}"
                    )
                    Result.failure(RuntimeException(apiResponse?.message ?: "Unknown error"))
                }
            } else {
                Log.e("VerifyOtp", "HTTP error: ${response.code()} - ${response.message()}")
                Result.failure(RuntimeException("Failed to verify OTP: ${response.message()}"))
            }
        } catch (e: Exception) {
            Log.e("VerifyOtp", "OTP verification exception: ${e.localizedMessage}", e)
            Result.failure(e)
        }
    }


    suspend fun registerUser(
        phoneNumber: String,
        username: String,
        password: String
    ): Result<UserResponse> {
        return try {
            Log.d(
                "RegisterUser",
                "Registering user with phone number: +91$phoneNumber, username: $username"
            )
            val response =
                authApiService.registerUser(RegisterRequest("+91$phoneNumber", username, password))

            if (response.isSuccessful) {
                val apiResponse = response.body()
                if (apiResponse?.success == true) {
                    val userResponse = apiResponse.data
                    Log.d(
                        "RegisterUser",
                        "Registration successful for user: $username with phone number: +91$phoneNumber"
                    )
                    Log.d("RegisterUser", "userId: ${userResponse.userId}")
                    Log.d("RegisterUser", "username: ${userResponse.username}")
                    Log.d("RegisterUser", "email: ${userResponse.email}")
                    Log.d("RegisterUser", "token: ${userResponse.token}")
                    Result.success(userResponse)
                } else {
                    Log.e(
                        "RegisterUser",
                        "Registration failed: Empty or unsuccessful response body"
                    )
                    Result.failure(RuntimeException("Empty or unsuccessful response body"))
                }
            } else {
                Log.e(
                    "RegisterUser",
                    "Registration HTTP error: ${response.code()} - ${response.message()}"
                )
                Result.failure(RuntimeException("Failed to register: ${response.message()}"))
            }
        } catch (e: Exception) {
            Log.e("RegisterUser", "Registration exception: ${e.localizedMessage}", e)
            Result.failure(e)
        }
    }


    suspend fun loginUser(phoneNumber: String, password: String): Result<UserResponse> {
        return try {
            Log.d("LoginUser", "password: $password")
            Log.d("LoginUser", "phoneNumber: $phoneNumber")
            val response = authApiService.loginUser(LoginRequest("+91$phoneNumber", password))
            if (response.isSuccessful) {
                val apiResponse = response.body()
                if (apiResponse?.success == true) {
                    val userResponse = apiResponse.data
                    Log.d("LoginUser", "Login successful for user: $phoneNumber")
                    Log.d("LoginUser", "userId: ${userResponse.userId}")
                    Log.d("LoginUser", "username: ${userResponse.username}")
                    Log.d("LoginUser", "email: ${userResponse.email}")
                    Log.d("LoginUser", "token: ${userResponse.token}")
                    Log.d("LoginUser", "expirationTime: ${userResponse.expirationTime}")
                    Result.success(userResponse)
                } else {
                    Log.e("LoginUser", "Login failed: Empty response body")
                    Result.failure(RuntimeException("Empty response body"))
                }
            } else {
                Log.e("LoginUser", "Login failed: ${response.message()}")
                Result.failure(RuntimeException("Failed to login: ${response.message()}"))
            }
        } catch (e: Exception) {
            Log.e("LoginUser", "Login exception: ${e.localizedMessage}", e)
            Result.failure(e)
        }
    }

    suspend fun updateUsername(token: String, username: String): Result<Boolean> {
        return try {
            val response = authApiService.updateUsername(token, UsernameRequest(username))
            if (response.isSuccessful) {
                val apiResponse = response.body()
                if (apiResponse?.success == true) {
                    Result.success(true)
                } else {
                    Result.failure(RuntimeException(apiResponse?.message))
                }
            } else {
                Result.failure(RuntimeException(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun sendOtpToEmail(token: String, email: String): Result<Boolean> {
        return try {
            val response = authApiService.sendEmailRequest(token, EmailRequest(email))
            Log.i("AuthRepository", token)
            Log.i("AuthRepository", email)
            if (response.isSuccessful) {
                val apiResponse = response.body()
                if (apiResponse?.success == true) {
                    Result.success(true)
                } else {
                    Result.failure(RuntimeException(apiResponse?.message))
                }
            } else {
                Result.failure(RuntimeException(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun verifyEmail(token: String, email: String, otp: String): Result<Boolean> {
        return try {
            val response = authApiService.verifyEmailRequest(token, EmailRequest(email, otp))
            if (response.isSuccessful) {
                val apiResponse = response.body()
                if (apiResponse?.success == true) {
                    Result.success(true)
                } else {
                    Result.failure(RuntimeException(apiResponse?.message))
                }
            } else {
                Result.failure(RuntimeException(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}