package com.example.furryroyals.model

import java.time.LocalDateTime

data class User(
    var userId: String = "",
    var username: String = "",
    var phoneNumber: String = "",
    var password: String = "",
    var email: String = "",
    var otp: String = "",
    var phoneNumberVerified: Boolean = false,
    var emailVerified: Boolean = false,
    var emailOtp: String = "",
    var phoneNumberOtp: String = "",
    var otpExpirationTime: LocalDateTime,
    var createdAt: LocalDateTime,
    var roles: String = ""
)