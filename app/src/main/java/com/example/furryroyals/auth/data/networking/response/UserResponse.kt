package com.example.furryroyals.auth.data.networking.response

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val userId: String, val username: String? = "", val email: String,
    val phoneNumber: String, val token: String, val expirationTime: Long
)