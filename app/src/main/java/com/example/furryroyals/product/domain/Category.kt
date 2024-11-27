package com.example.furryroyals.product.domain

data class Category(
    val id: String,
    val name: String,
    val productIds: List<String> = emptyList(),
    val localId: Long = 0
)