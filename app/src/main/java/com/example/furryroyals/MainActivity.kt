package com.example.furryroyals

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.furryroyals.ui.theme.FurryRoyalsTheme
import com.example.furryroyals.core.presentation.nav_items.bottomNav.BottomAppbar
import com.example.furryroyals.core.presentation.nav_items.topNav.TopAppbar
import com.example.furryroyals.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FurryRoyalsTheme {
                val navController: NavHostController = rememberNavController()
                var bottomBarVisible by remember { mutableStateOf(true) }
                var cartButtonVisibility by remember { mutableStateOf(true) }

                Scaffold(
                    topBar = {
                        TopAppbar(
                            navController = navController,
                            modifier = Modifier.height(70.dp),
                            cartButtonVisibility = cartButtonVisibility
                        )
                    },
                    bottomBar = {
                        if (bottomBarVisible) {
                            BottomAppbar(
                                navController = navController,
                                modifier = Modifier.height(75.dp)
                            )
                        }
                    }
                ) { paddingValues ->
                    Box(modifier = Modifier.padding(paddingValues)) {
                        AppNavigation(
                            navController = navController,
                            onBottomBarVisibilityChanged = { bottomBarVisible = it },
                            onButtonsVisibilityChanged = { cartButtonVisibility = it })
                    }
                }

            }
        }

    }

}


