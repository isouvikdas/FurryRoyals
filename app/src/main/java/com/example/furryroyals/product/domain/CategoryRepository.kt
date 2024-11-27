package com.example.furryroyals.product.domain

import com.example.furryroyals.product.data.local.CategoryEntity

interface CategoryRepository {
    suspend fun fetchAndStoreCategories(): Boolean
    suspend fun clearAllCategories()
    suspend fun fetchAllCategories(): List<CategoryEntity>
}