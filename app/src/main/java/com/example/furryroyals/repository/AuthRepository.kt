package com.example.furryroyals.repository

import android.content.SharedPreferences

class AuthRepository(
    private val sharedPreferences: SharedPreferences
) {

    companion object {
        private const val TOKEN_KEY = "auth_token"
        private const val EXPIRATION_TIME_KEY = "token_expiration_time"
    }

    fun saveToken(token: String, expirationTime: Long) {
        sharedPreferences.edit().apply {
            putString(TOKEN_KEY, token)
            putLong(EXPIRATION_TIME_KEY, expirationTime)
            apply()

        }
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

    fun clearToken() {
        sharedPreferences.edit().remove(TOKEN_KEY).remove(EXPIRATION_TIME_KEY).apply()
    }
}