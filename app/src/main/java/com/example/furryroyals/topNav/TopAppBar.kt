package com.example.furryroyals.topNav

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.compose.FurryRoyalsTheme
import com.example.furryroyals.R
import com.example.furryroyals.bottomNav.BottomNavigationItems

@Composable
fun TopAppbar(
    navController: NavHostController,
    modifier: Modifier,
    cartButtonVisibility: Boolean,
) {
    NavigationBar(
        modifier = modifier.fillMaxHeight(0.085f),
        containerColor = Color.White,
        tonalElevation = 20.dp
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        Row(
            modifier = Modifier
                .fillMaxWidth(0.68f)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Image(
                painter = painterResource(id = R.drawable.furryroyals_high_resolution_logo_transparent5),
                contentDescription = "Logo",
                modifier = Modifier
                    .height(80.dp)
                    .width(130.dp)
                    .align(Alignment.CenterVertically)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            if (cartButtonVisibility) {
                BottomNavigationItems.Cart?.let {
                    NavigationBarItem(
                        icon = { Icon(imageVector = it.icon!!, contentDescription = null) },
                        selected = currentRoute == it.route,
                        onClick = {
                            if (currentRoute != it.route) { // Prevent redundant navigation
                                navController.navigate(it.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    )

                }
            }
        }
    }
}



@Preview
@Composable
fun TopAppBarPreview() {
    FurryRoyalsTheme {
        val topBarVisible by remember { mutableStateOf(true) }
        val navController: NavHostController = rememberNavController()
        Scaffold(
            topBar = {
                if (topBarVisible) {
                    TopAppbar(
                        navController,
                        modifier = Modifier,
                        true
                    )
                }
            }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues))
        }
    }
}
