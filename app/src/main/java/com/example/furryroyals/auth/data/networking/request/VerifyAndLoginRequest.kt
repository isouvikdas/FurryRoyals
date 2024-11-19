package com.example.furryroyals.auth.data.networking.request

import kotlinx.serialization.Serializable

@Serializable
data class VerifyAndLoginRequest(
    val phoneNumber: String,
    val otp: String
)

