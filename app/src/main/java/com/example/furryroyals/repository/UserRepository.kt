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
        token: String? = null,
        expirationTime: Long? = null,
        userId: String? = null,
        phoneNumber: String? = null,
        username: String? = null,
    ) {
        sharedPreferences.edit().apply {
            token?.let { putString(TOKEN_KEY, it) }
            expirationTime?.let { putLong(EXPIRATION_TIME_KEY, it) }
            userId?.let { putString(USER_ID_KEY, it) }
            phoneNumber?.let { putString(PHONE_NUMBER_KEY, it) }
            username?.let { putString(USERNAME_KEY, it) }
            apply()
        }
    }

    fun saveUserEmail(email: String? = null) {
        sharedPreferences.edit().apply {
            email?.let {
                putString(EMAIL_KEY, it)
            }
            apply()
        }
    }

    fun saveUsername(username: String? = null) {
        sharedPreferences.edit().apply {
            username?.let {
                putString(EMAIL_KEY, it)
            }
            apply()
        }
    }

    fun getUserId(): String? = sharedPreferences.getString(USER_ID_KEY, "")
    fun getUserName(): String? = sharedPreferences.getString(USERNAME_KEY, "")
    fun getUserEmail(): String? = sharedPreferences.getString(EMAIL_KEY, "")
    fun getUserPhoneNumber(): String? = sharedPreferences.getString(PHONE_NUMBER_KEY, "")


    fun getToken(): String? = sharedPreferences.getString(TOKEN_KEY, "")
    private fun getExpirationTime(): Long = sharedPreferences.getLong(EXPIRATION_TIME_KEY, 0)

    fun isLoggedIn(): Boolean {
        val token = getToken()
        val expirationTime = getExpirationTime()
        return !token.isNullOrEmpty()  && System.currentTimeMillis() < expirationTime
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