package com.example.furryroyals.product.presentation.produtc_list

import com.example.furryroyals.product.domain.Product

data class ProductListState(
    val productList: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val endOfPaginationReached: Boolean = false,
    val selectedProduct: Product? = null,
    val currentCursor: String? = null,
    val localCursor: Long = 0,
    val errorMessage: String? = null
)
