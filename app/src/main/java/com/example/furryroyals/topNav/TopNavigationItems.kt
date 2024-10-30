package com.example.furryroyals.topNav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class TopNavigationItems(
    val route: String,
    val icon: ImageVector? = null
) {
    data object Cart: TopNavigationItems(
        route = "Cart",
        icon = Icons.Outlined.ShoppingCart
    )

    data object Profile: TopNavigationItems(
        route = "Profile",
        icon = Icons.Outlined.AccountCircle
    )
}