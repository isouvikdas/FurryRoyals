package com.example.furryroyals.topNav

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.compose.FurryRoyalsTheme
import com.example.furryroyals.R

@Composable
fun TopAppbar(
    navController: NavHostController,
    modifier: Modifier
) {
    val screens = listOf(
        TopNavigationItems.Cart,
        TopNavigationItems.Profile
    )

    NavigationBar(
        modifier = modifier,
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
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {

            IconButton(onClick = {}) {
                TopNavigationItems.Profile.icon?.let {
                    Icon(
                        imageVector = it,
                        contentDescription = "Profile"
                    )
                }
            }

            IconButton(onClick = {}) {
                TopNavigationItems.Cart.icon?.let {
                    Icon(
                        imageVector = it,
                        contentDescription = "Cart"
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
        val navController: NavHostController = rememberNavController()
        var topBarVisible by remember { mutableStateOf(true) }

        Scaffold(
            topBar = {
                if (topBarVisible) {
                    TopAppbar(
                        navController = navController,
                        modifier = Modifier

                    )
                }
            }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues))
        }
    }
}
