package com.example.furryroyals.product.domain

import android.graphics.Bitmap
import com.example.furryroyals.product.data.local.ProductEntity

interface ProductRepository {
    suspend fun fetchLastFetchedPage(localCursor: Long): List<ProductEntity>
    suspend fun fetchAndStoreProducts(cursor: String?): String?
    suspend fun clearAllProducts()
}