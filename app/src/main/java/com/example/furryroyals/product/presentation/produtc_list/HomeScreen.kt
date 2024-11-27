package com.example.furryroyals.product.presentation.produtc_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.furryroyals.ui.theme.FurryRoyalsTheme
import com.example.furryroyals.product.domain.Category
import com.example.furryroyals.product.domain.Product

@Composable
fun HomeScreen(
    productListState: ProductListState,
    categoryListState: CategoryListState
) {
    Surface(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            SearchBar(
                value = "", Modifier.padding(
                    vertical = 10.dp, horizontal = 20.dp
                )
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    CategoryRow(
                        modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp),
                        categoryListState.categoryList
                    )
                }
                item {
                    HomeProductRow(
                        products = productListState.productList,
                        title = "New Launches",
                        maxItems = 4
                    ) {
                    }
                }

                item {
                    HomeProductRow(
                        products = productListState.productList,
                        title = "Featured Products",
                        maxItems = 3
                    ) {
                    }
                }
            }
        }
    }
}

@Composable
fun SearchBar(
    value: String,
    modifier: Modifier = Modifier
) {
    var query by remember { mutableStateOf(value) }
    var active by remember { mutableStateOf(false) }
    var clearButtonClicked by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = query,
        onValueChange = {
            query = it
            clearButtonClicked = false
        },
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        placeholder = {
            Text(text = "Search here", style = MaterialTheme.typography.bodyLarge)
        },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
        },
        trailingIcon = {
            if (query.isNotEmpty() || active) {
                IconButton(
                    onClick = {
                        if (!clearButtonClicked) {
                            query = ""
                            clearButtonClicked = true
                        } else {
                            active = false
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear Icon"
                    )
                }
            }
        },
        shape = RoundedCornerShape(16.dp),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.DarkGray,
            unfocusedBorderColor = Color.DarkGray,
            cursorColor = Color.Black,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black
        ),
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategoryRow(
    modifier: Modifier = Modifier,
    categories: List<Category>
) {
    var selectedCategory by remember { mutableStateOf<Category?>(null) }
    Column(modifier = modifier.wrapContentHeight()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Categories",
                style = MaterialTheme.typography.titleLarge
            )

            Row(modifier = Modifier.clickable { }) {
                Text(
                    text = "See all",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.tertiary
                )

                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowForward,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.tertiary
                )

            }

        }

        FlowRow(
            maxItemsInEachRow = 3,
        ) {
            categories.forEach { category ->
                val isSelected = selectedCategory == category

                FilterChip(
                    onClick = {
                        selectedCategory = if (isSelected) null else category
                    },
                    label = { Text(category.name) },
                    selected = isSelected,
                    leadingIcon = if (isSelected) {
                        {
                            Icon(
                                imageVector = Icons.Default.Done,
                                contentDescription = null,
                                modifier = Modifier.size(FilterChipDefaults.IconSize)
                            )
                        }
                    } else null,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.padding(end = 8.dp),
                    colors = FilterChipDefaults.filterChipColors(selectedContainerColor = MaterialTheme.colorScheme.surfaceContainerHighest)
                )
            }
        }

    }
}

@Composable
fun HomeProductRow(
    products: List<Product>,
    title: String,
    maxItems: Int,
    onClick: (Product) -> Unit
) {
    Column(modifier = Modifier
        .wrapContentHeight()
        .padding(bottom = 20.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
            )
            Row(modifier = Modifier
                .clickable { }
            ) {
                Text(
                    text = "See all",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.tertiary,
                )

                Icon(
                    modifier = Modifier.padding(end = 20.dp),
                    imageVector = Icons.AutoMirrored.Default.ArrowForward,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.tertiary
                )

            }
        }
        Spacer(modifier = Modifier.size(8.dp))
        LazyRow {
            items(products.take(maxItems), key = { it.id }) { product ->
                val isVisible = remember {
                    mutableStateOf(false)
                }
                LaunchedEffect(true) {
                    isVisible.value = true
                }
                AnimatedVisibility(
                    visible = isVisible.value, enter = fadeIn() + expandVertically()
                ) {
                    ProductItem(
                        modifier = Modifier.padding(start = 20.dp),
                        product = product,
                        onClick
                    )
                }
            }
        }
    }
}


@Preview
@Composable
private fun HomeScreenPreview() {
    FurryRoyalsTheme {
        HomeScreen(
            productListState = ProductListState(),
            categoryListState = CategoryListState()
        )
    }
}


