package com.example.furryroyals.product.presentation.produtc_list

import android.util.Log
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.furryroyals.R
import com.example.furryroyals.product.data.mappers.toCategory
import com.example.furryroyals.product.data.mappers.toProduct
import com.example.furryroyals.product.domain.CategoryRepository
import com.example.furryroyals.product.domain.ProductRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

class ProductViewModel(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _productListState = MutableStateFlow(ProductListState())
    val productListState: StateFlow<ProductListState> = _productListState.asStateFlow()

    private val _categoryListState = MutableStateFlow(CategoryListState())
    val categoryListState: StateFlow<CategoryListState> = _categoryListState.asStateFlow()

    init {

        clearCachedData()
        fetchCategories()
        fetchNextPage()
    }

    private fun clearCachedData() {
        viewModelScope.launch {
            async {
                categoryRepository.clearAllCategories()
                productRepository.clearAllProducts()
            }.await()
        }
    }

    fun fetchCategories() {
        if (_categoryListState.value.isLoading) return
        viewModelScope.launch {
            try {
                _categoryListState.update { it.copy(isLoading = true, errorMessage = null) }

                val isListReceived = categoryRepository.fetchAndStoreCategories()
                Log.i("category in viewmodel", isListReceived.toString())
                if (isListReceived) {
                    val categories = categoryRepository.fetchAllCategories()
                    categories.forEach {
                        Log.i("category in viewmodel", it.name)
                    }
                    _categoryListState.update {
                        it.copy(
                            categoryList = it.categoryList + categories.map { categoryEntity ->
                                categoryEntity.toCategory()
                            },
                        )
                    }
                } else {
                    _categoryListState.update {
                        it.copy(errorMessage = "Failed to fetch categories")
                    }
                }
            } catch (e: Exception) {
                Timber.e(e, "Error fetching categories")
            } finally {
                _categoryListState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun fetchNextPage() {
        if (_productListState.value.endOfPaginationReached || _productListState.value.isLoading) return
        viewModelScope.launch {
            try {
                _productListState.update { it.copy(isLoading = true, errorMessage = null) }

                val nextCursor = productRepository.fetchAndStoreProducts(
                    _productListState.value.currentCursor
                )

                val newProducts =
                    productRepository.fetchLastFetchedPage(_productListState.value.localCursor)

                if (newProducts.isNotEmpty()) {
                    _productListState.update {
                        it.copy(
                            productList = it.productList + newProducts.map { productEntity ->
                                productEntity.toProduct()
                            },
                            currentCursor = nextCursor,
                            localCursor = newProducts.last().localId,
                        )
                    }
                } else {
                    _productListState.update {
                        it.copy(
                            endOfPaginationReached = true
                        )
                    }
                }
            } catch (e: Exception) {
                Timber.e(e, "Error fetching products")
            } finally {
                _productListState.update { it.copy(isLoading = false) }
            }
        }
    }
}

