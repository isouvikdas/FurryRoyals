package com.example.furryroyals.product.presentation.produtc_list

import com.example.furryroyals.product.domain.Category

data class CategoryListState(
    val categoryList: List<Category> = emptyList(),
    val isLoading: Boolean = false,
    val selectedCategory: Category? = null,
    val errorMessage: String? = null
)
