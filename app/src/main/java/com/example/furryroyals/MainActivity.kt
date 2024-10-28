package com.example.furryroyals

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import com.example.furryroyals.ui.navigation.AppNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FurryRoyalsTheme {
                val navController: NavHostController = rememberNavController()
                var buttonsVisible by remember {
                    mutableStateOf(true)
                }

                Scaffold(bottomBar =
                {
                    if (buttonsVisible) {
                        BottomAppbar(
                            navController = navController,
                            modifier = Modifier
                        )
                    }
                }) { paddingValues ->
                    Box(
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        AppNavigation(navController = navController) { isVisible ->
                            buttonsVisible = isVisible
                        }
                    }
                }
            }

        }
    }
}

