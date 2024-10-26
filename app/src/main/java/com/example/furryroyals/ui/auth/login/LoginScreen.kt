package com.example.furryroyals.ui.auth.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.furryroyals.ui.isSmallScreenHeight
import com.example.furryroyals.ui.rememberImeState
import com.example.furryroyals.ui.theme.FurryRoyalsTheme
import com.example.furryroyals.ui.component.PasswordTextField
import com.example.furryroyals.ui.component.TextTextField
import kotlin.math.log

@Composable
fun AnimatedLoginScreen(
    modifier: Modifier = Modifier,
    onLoginSuccess: () -> Unit,
    onSingUpClick: () -> Unit
) {
    var visible by remember { mutableStateOf(false) }
    val isImeVisible by rememberImeState()

    LaunchedEffect(Unit) {
        visible = true
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        AnimatedVisibility(
            visible = visible,
            enter = slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
            ),
            exit = slideOutVertically(
                targetOffsetY = { it },
                animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
            )
        ) {
            LoginScreen(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(if (isImeVisible) 0.905f else 0.6f),
                onLoginSuccess = onLoginSuccess,
                onSingUpClick = onSingUpClick

            )
        }
    }
}

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onLoginSuccess: () -> Unit,
    onSingUpClick: () -> Unit
) {
    val loginViewModel: LoginViewModel = hiltViewModel()
    val loginUiState = loginViewModel.loginUiState

    var phoneNumber by rememberSaveable { mutableStateOf(loginUiState.phoneNumber) }
    var password by rememberSaveable { mutableStateOf(loginUiState.password) }

    Surface(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)),
        color = Color.White,
        shadowElevation = 10.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isSmallScreenHeight()) {
                Spacer(modifier = Modifier.fillMaxSize(0.05f))
            } else {
                Spacer(modifier = Modifier.fillMaxSize(0.1f))
            }

            Text(
                text = "Sign In",
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                fontSize = 25.sp,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Start)
            )

            if (isSmallScreenHeight()) {
                Spacer(modifier = Modifier.fillMaxSize(0.04f))
            } else {
                Spacer(modifier = Modifier.fillMaxSize(0.06f))
            }

            TextTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                hint = "Mobile Number",
                leadingIcon = Icons.Default.Phone,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                keyboardType = KeyboardType.Phone,
                textColor = Color.Black
            )

            Spacer(modifier = Modifier.height(20.dp))

            PasswordTextField(
                value = password,
                onValueChange = { password = it },
                hint = "Password",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                textColor = Color.Black
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Forgot Password?",
                    fontSize = 16.sp,
                    modifier = Modifier.clickable { }
                )
            }

            Button(
                onClick = {
                    loginViewModel.login(phoneNumber, password)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                enabled = phoneNumber.length == 10 && password.isNotEmpty() && !loginUiState.isLoading
            ) {
                if (loginUiState.isLoading) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text(
                        text = "Sign In",
                        fontSize = 18.sp,
                        modifier = Modifier
                            .padding(vertical = 4.dp)
                    )
                }
            }

            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 10.dp)
            ) {
                Text(
                    text = "Don't have an account? ",
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Text(
                    text = "Sign Up",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable { onSingUpClick() }
                )
            }

            Spacer(modifier = Modifier.height(1.dp))

            loginUiState.errorMessage?.let { errorMessage ->
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 14.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            if (loginUiState.loginSuccess) {
                onLoginSuccess()
            }
        }
    }
}


