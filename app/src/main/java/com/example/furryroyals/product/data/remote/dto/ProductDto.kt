package com.example.furryroyals.product.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val inventory: Int,
    val categoryName: String,
    val imageIds: List<String> = ArrayList(),
    val firstImageId: String? = null,
)
