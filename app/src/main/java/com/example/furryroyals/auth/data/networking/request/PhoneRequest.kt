package com.example.furryroyals.auth.data.networking.request

import kotlinx.serialization.Serializable

@Serializable
data class PhoneRequest(val phoneNumber: String)
