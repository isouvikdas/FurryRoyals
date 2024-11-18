package com.example.furryroyals.core.presentation.nav_items.bottomNav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavigationItems(
    val route: String,
    val title: String? = null,
    val icon: ImageVector? = null
) {
    data object Home: BottomNavigationItems(
        route = "Home",
        title = "Home",
        icon = Icons.Outlined.Home
    )

    data object Category: BottomNavigationItems(
        route = "Category",
        title = "Category",
        icon = Icons.Outlined.Category
    )

    data object Cart: BottomNavigationItems(
        route = "Cart",
        title = "Cart",
        icon = Icons.Outlined.ShoppingCart
    )

    data object Profile: BottomNavigationItems(
        route = "Profile",
        title = "Profile",
        icon = Icons.Outlined.AccountCircle
    )
}