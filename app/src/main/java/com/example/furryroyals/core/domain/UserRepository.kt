package com.example.furryroyals.core.domain

import android.content.SharedPreferences
import android.util.Log

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

    fun clearOnInconsistentData() {
        val token = sharedPreferences.getString(TOKEN_KEY, "")
        val username = sharedPreferences.getString(USERNAME_KEY, "")
        val phoneNumber = sharedPreferences.getString(PHONE_NUMBER_KEY, "")
        val expirationTime = sharedPreferences.getLong(EXPIRATION_TIME_KEY, 0L)
        val userId = sharedPreferences.getString(USER_ID_KEY, "")
        val email = sharedPreferences.getString(EMAIL_KEY, "")

        val currentTime = System.currentTimeMillis()

        val editor = sharedPreferences.edit()
        if (!token.isNullOrEmpty()) {
            editor.remove(TOKEN_KEY)
        }
        if (!username.isNullOrEmpty()) {
            editor.remove(USERNAME_KEY)
        }
        if (!phoneNumber.isNullOrEmpty()) {
            editor.remove(PHONE_NUMBER_KEY)
        }
        if (expirationTime == 0L || expirationTime < currentTime) {
            editor.remove(EXPIRATION_TIME_KEY)
        }
        if (!email.isNullOrEmpty()) {
            editor.remove(EMAIL_KEY)
        }
        if (!userId.isNullOrEmpty()) {
            editor.remove(USER_ID_KEY)
        }
        editor.apply()
    }



    fun saveUserData(
        token: String? = null,
        expirationTime: Long? = null,
        userId: String? = null,
        phoneNumber: String? = null,
        username: String? = null,
    ) {
        sharedPreferences.edit().apply {
            token?.let {
                putString(TOKEN_KEY, it)
                Log.i("toggle", "savedToken: $token")
            }
            expirationTime?.let {
                putLong(EXPIRATION_TIME_KEY, it)
                Log.i("toggle", "savedExpirationTime: $expirationTime")

            }
            userId?.let {
                putString(USER_ID_KEY, it)
                Log.i("toggle", "savedUserId: $userId")
            }
            phoneNumber?.let {
                putString(PHONE_NUMBER_KEY, it)
                Log.i("toggle", "savedPhoneNumber: $phoneNumber")
            }
            username?.let {
                putString(USERNAME_KEY, it)
                Log.i("toggle", "savedUsername: $username")

            }
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
                putString(USERNAME_KEY, it)
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