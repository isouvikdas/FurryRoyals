package com.example.furryroyals.product.data.remote

import android.content.Context
import androidx.room.withTransaction
import com.example.furryroyals.core.domain.util.onError
import com.example.furryroyals.core.domain.util.onSuccess
import com.example.furryroyals.product.data.local.ProductDatabase
import com.example.furryroyals.product.data.local.ProductEntity
import com.example.furryroyals.product.data.mappers.toProductEntity
import com.example.furryroyals.product.domain.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ProductRepositoryImpl(
    private val productApi: ProductApi,
    private val productDb: ProductDatabase,
    private val context: Context
) : ProductRepository {

    override suspend fun fetchLastFetchedPage(localCursor: Long): List<ProductEntity> =
        withContext(Dispatchers.IO) {
            productDb.productDao.getProductsAfterCursor(localCursor)
        }

    override suspend fun fetchAndStoreProducts(cursor: String?): String? {
        try {
            val result = productApi.getAllProducts(cursor)

            result.onSuccess { apiResponse ->
                if (!apiResponse.success) {
                    throw IOException(apiResponse.message)
                }

                val pagedData = apiResponse.data
                val products = pagedData?.products?.map { it.toProductEntity() } ?: emptyList()

                if (products.isNotEmpty()) {
                    storeProductsWithCaching(products)
                }

                return pagedData?.nextCursor
            }.onError { error ->
                throw IOException("API Error: $error")
            }

        } catch (e: IOException) {
            Timber.e(e, "Network or API error occurred.")
            return null
        } catch (e: Exception) {
            Timber.e(e, "Unexpected error occurred.")
            return null
        }
        return null
    }

    private suspend fun storeProductsWithCaching(products: List<ProductEntity>) {
        val updatedProducts = withContext(Dispatchers.IO) {
            products.map { product ->
                async {
                    try {
                        val firstImageUri = cacheFirstImage(product, context)
                        product.copy(firstImageUri = firstImageUri)
                    } catch (e: Exception) {
                        Timber.e(e, "Error caching image for product ID: ${product.id}")
                        product // Return original product if caching fails
                    }
                }
            }.awaitAll() // Wait for all async tasks to complete
        }

        // Insert products into the database
        productDb.withTransaction {
            productDb.productDao.upsertAll(updatedProducts)
        }
    }


    private suspend fun cacheFirstImage(productEntity: ProductEntity, context: Context): String {
        try {
            val imageResult = productEntity.firstImageId?.let { productApi.fetchFirstImage(it) }
            var cachedFilePath: String? = null

            imageResult?.onSuccess { byteArray ->
                val cachedFile =
                    saveToDiskCache(byteArray, productEntity.id, context.cacheDir)
                cachedFilePath = cachedFile?.absolutePath

            }?.onError { error ->
                throw RuntimeException("Network error: $error")
            }

            return cachedFilePath ?: throw IllegalStateException("Failed to cache the image")
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }


    private suspend fun saveToDiskCache(
        byteArray: ByteArray,
        uniqueId: String,
        cacheDir: File
    ): File? {
        val file = File(cacheDir, "$uniqueId.jpg")
        return try {
            withContext(Dispatchers.IO) {
                FileOutputStream(file).use { outputStream ->
                    outputStream.write(byteArray)
                }
            }
            file
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun clearAllProducts() {
        withContext(Dispatchers.IO) {
            productDb.withTransaction {
                productDb.productDao.clearAll()
                clearImageCached()
            }
        }
    }

    private fun clearImageCached() {
        val cacheDir = context.cacheDir
        if (cacheDir.exists()) {
            val files = cacheDir.listFiles()
            files?.forEach { file ->
                if (file.isFile && file.name.endsWith(".jpg")) {
                    file.delete()
                }
            }
        }
    }
}