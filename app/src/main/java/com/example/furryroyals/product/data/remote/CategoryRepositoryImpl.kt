package com.example.furryroyals.product.data.remote

import android.content.Context
import android.util.Log
import androidx.room.withTransaction
import com.example.furryroyals.core.domain.util.onError
import com.example.furryroyals.core.domain.util.onSuccess
import com.example.furryroyals.product.data.local.CategoryEntity
import com.example.furryroyals.product.data.local.ProductDatabase
import com.example.furryroyals.product.data.mappers.toCategoryEntity
import com.example.furryroyals.product.domain.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.IOException

class CategoryRepositoryImpl(
    private val productDb: ProductDatabase,
    private val productApi: ProductApi
) : CategoryRepository {

    override suspend fun fetchAllCategories(): List<CategoryEntity> = withContext(Dispatchers.IO) {
        productDb.categoryDao.getCategories()

    }


    override suspend fun fetchAndStoreCategories(): Boolean {
        var isListReceived = false
        try {
            val result = productApi.getAllCategories()
            result.onSuccess { apiResponse ->
                if (!apiResponse.success) {
                    throw IOException(apiResponse.message)
                }
                val categories = apiResponse.data?.map { it.toCategoryEntity() } ?: emptyList()
                if (categories.isNotEmpty()) {
                    withContext(Dispatchers.IO) {
                        productDb.withTransaction {
                            productDb.categoryDao.upsertAll(categories)
                            isListReceived = true

                        }
                    }
                }
            }.onError { error ->
                throw IOException("Api Error: $error")

            }
        } catch (e: IOException) {
            Timber.e(e, "Network or API error occurred.")
        } catch (e: Exception) {
            Timber.e(e, "Unexpected error occurred.")
        }
        return isListReceived
    }

    override suspend fun clearAllCategories() {
        withContext(Dispatchers.IO) {
            productDb.withTransaction {
                productDb.categoryDao.clearAll()
            }
        }
    }
}