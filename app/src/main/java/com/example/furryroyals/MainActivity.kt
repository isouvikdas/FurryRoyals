package com.example.furryroyals

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.compose.FurryRoyalsTheme
import com.example.furryroyals.bottomNav.BottomAppbar
import com.example.furryroyals.topNav.TopAppbar
import com.example.furryroyals.ui.navigation.AppNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
                            modifier = Modifier,
                            cartButtonVisibility = cartButtonVisibility,

                        )
                    },
                    bottomBar = {
                        if (bottomBarVisible) {
                            BottomAppbar(navController = navController, modifier = Modifier)
                        }
                    }
                ) { paddingValues ->
                    Box(modifier = Modifier.padding(paddingValues)) {
                        AppNavigation(
                            navController = navController,
                            onBottomBarVisibilityChanged = { bottomBarVisible = it },
                            onButtonsVisibilityChanged = { cartButtonVisibility = it }

                        )
                    }
                }

            }
        }

    }
}


