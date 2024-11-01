package com.example.furryroyals.repository

import android.content.SharedPreferences

class UserRepository(
    private val sharedPreferences: SharedPreferences
) {
    companion object {
        private const val TOKEN_KEY = "auth_token"
        private const val EXPIRATION_TIME_KEY = "token_expiration_time"
        private const val USERNAME_KEY = "username"
        private const val USER_ID_KEY = "user_id"
        private const val EMAIL_KEY = "email"
        private const val PHONE_NUMBER_KEY = "phone_number"
    }

    fun saveUserData(
        token: String,
        expirationTime: Long,
        userId: String,
        phoneNumber: String,
        username: String
    ) {
        sharedPreferences.edit().apply {
            putString(TOKEN_KEY, token)
            putLong(EXPIRATION_TIME_KEY, expirationTime)
            putString(USER_ID_KEY, userId)
            putString(PHONE_NUMBER_KEY, phoneNumber)
            putString(USERNAME_KEY, username)
            apply()

        }
    }

    fun saveUserData(email: String) {
        sharedPreferences.edit().apply {
            putString(EMAIL_KEY, email)
            apply()
        }
    }

    fun getUserName(): String? {
        return sharedPreferences.getString(USERNAME_KEY, null)
    }

    fun getUserId(): String? {
        return sharedPreferences.getString(USER_ID_KEY, null)
    }

    fun getUserEmail(): String? {
        return sharedPreferences.getString(EMAIL_KEY, null)
    }

    fun getUserPhoneNumber(): String? {
        return sharedPreferences.getString(PHONE_NUMBER_KEY, null)
    }


    private fun getToken(): String? {
        return sharedPreferences.getString(TOKEN_KEY, null)
    }

    private fun getExpirationTime(): Long {
        return sharedPreferences.getLong(EXPIRATION_TIME_KEY, 0)
    }

    fun isLoggedIn(): Boolean {
        val token = getToken()
        val expirationTime = getExpirationTime()
        return token != null && System.currentTimeMillis() < expirationTime
    }

    fun clearUserData(): Result<Boolean> {
        return try {
            val isSuccess = sharedPreferences.edit()
                .remove(TOKEN_KEY)
                .remove(EXPIRATION_TIME_KEY)
                .remove(EMAIL_KEY)
                .remove(USER_ID_KEY)
                .remove(USERNAME_KEY)
                .remove(PHONE_NUMBER_KEY)
                .commit()
            Result.success(isSuccess)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}