package com.example.furryroyals

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.furryroyals.navigation.AppNavigation
import com.example.furryroyals.ui.auth.login.AnimatedLoginScreen
import com.example.furryroyals.ui.theme.FurryRoyalsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FurryRoyalsTheme {
                AppNavigation()
//                    AnimatedRegisterScreen(modifier = Modifier.padding(innerPadding))
//                    AnimatedOtpScreen(modifier = Modifier.padding(innerPadding))
//                    AnimatedRegisterFinalScreen(modifier = Modifier.padding(innerPadding))
            }

        }
    }
}

