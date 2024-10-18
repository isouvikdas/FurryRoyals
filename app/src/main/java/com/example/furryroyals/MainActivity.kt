package com.example.furryroyals

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.furryroyals.auth.registration.RegisterScreen
import com.example.furryroyals.ui.theme.FurryRoyalsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FurryRoyalsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    LoginScreen(modifier = Modifier.padding(innerPadding))
                    RegisterScreen(modifier = Modifier.padding(innerPadding))
//                    OtpTextField(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

