package com.example.furryroyals.core.presentation.nav_items.bottomNav

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.ContentAlpha
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomAppbar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val screens = listOf(
        BottomNavigationItems.Home,
        BottomNavigationItems.Cart,
        BottomNavigationItems.WatchList,
        BottomNavigationItems.Profile
    )

    NavigationBar(
        modifier = modifier,
        containerColor = Color.White
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        screens.forEach { screen ->
            NavigationBarItem(
                label = {
                    Text(text = screen.title!!)
                },
                icon = {
                    Icon(
                        imageVector = if (currentRoute == screen.route) {
                            screen.selectedIcon!!
                        } else {
                            screen.unselectedIcon!!
                        },
                        contentDescription = screen.title
                    )
                },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = LocalContentColor.current, // Icon stays visible
                    unselectedIconColor = LocalContentColor.current.copy(alpha = ContentAlpha.medium),
                    selectedTextColor = LocalContentColor.current, // Text stays visible
                    unselectedTextColor = LocalContentColor.current.copy(alpha = ContentAlpha.medium),
                    indicatorColor = Color.Transparent // No highlight behind the icon
                )
            )
        }
    }
}
