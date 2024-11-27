package com.example.furryroyals.auth.data.networking.request

import kotlinx.serialization.Serializable

@Serializable
data class EmailRequest(
    val email: String,
    val otp: String
) {
    constructor(email: String) : this(email, otp = "")
}