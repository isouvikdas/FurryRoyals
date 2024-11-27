package com.example.furryroyals.product.data.remote

import com.example.furryroyals.core.data.networking.constructUrl
import com.example.furryroyals.core.data.networking.safeCall
import com.example.furryroyals.core.domain.util.NetworkError
import com.example.furryroyals.core.domain.util.Result
import com.example.furryroyals.core.domain.util.map
import com.example.furryroyals.core.response.ApiResponse
import com.example.furryroyals.product.data.remote.dto.CategoryDto
import com.example.furryroyals.product.data.remote.dto.PagedData
import com.example.furryroyals.product.data.remote.dto.ProductDto
import com.example.furryroyals.product.domain.Category
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class ProductApi(
    private val httpClient: HttpClient
) {
    suspend fun getAllProducts(cursor: String? = null):
            Result<ApiResponse<PagedData<ProductDto>>, NetworkError> {
        return safeCall {
            httpClient.get(
                urlString = constructUrl("products/all")
            ) {
                parameter("cursor", cursor)
            }
        }
    }

    suspend fun fetchFirstImage(imageId: String):
            Result<ByteArray, NetworkError> {
        return safeCall {
            httpClient.get(
                urlString = constructUrl("products/images/load/$imageId")
            )
        }
    }

    suspend fun getAllCategories():
            Result<ApiResponse<List<CategoryDto>>, NetworkError> {
        return safeCall {
            httpClient.get(
                urlString = constructUrl("products/categories/all")
            )
        }
    }
}