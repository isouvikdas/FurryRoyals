package com.example.furryroyals.core.response

data class ApiResponse<T>(val message: String, val success: Boolean, val data: T)
