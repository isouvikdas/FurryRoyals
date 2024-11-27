package com.example.furryroyals.product.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PageInfo(
    val size: Int,
    val totalPages: Int,
    val totalElements: Int,
    val number: Int
)
