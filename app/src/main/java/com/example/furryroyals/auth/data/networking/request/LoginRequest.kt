package com.example.furryroyals.auth.data.networking.request

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val phoneNumber: String,
    val otp: String
) {
    constructor(phoneNumber: String) : this(phoneNumber, otp = "")
}

