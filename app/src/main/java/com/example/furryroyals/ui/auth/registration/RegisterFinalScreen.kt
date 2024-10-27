package com.example.furryroyals.ui.auth.registration

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.furryroyals.ui.isSmallScreenHeight
import com.example.furryroyals.ui.rememberImeState
import com.example.furryroyals.ui.component.PasswordTextField
import com.example.furryroyals.ui.component.TextTextField

@Composable
fun AnimatedRegisterFinalScreen(
    modifier: Modifier = Modifier,
    registrationViewModel: RegistrationViewModel,
    registrationUiState: RegistrationUiState,
    onSuccess: () -> Unit,
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
            RegisterFinalScreen(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(if (isImeVisible) 0.82f else 0.5f),
                onSuccess = { onSuccess() },
                registrationViewModel = registrationViewModel,
                registrationUiState = registrationUiState

            )
        }
    }
}

@Composable
fun RegisterFinalScreen(
    modifier: Modifier = Modifier,
    registrationViewModel: RegistrationViewModel,
    registrationUiState: RegistrationUiState,
    onSuccess: () -> Unit,
) {

    var username by rememberSaveable { mutableStateOf(registrationUiState.username) }
    var password by rememberSaveable { mutableStateOf(registrationUiState.password) }

    LaunchedEffect(registrationUiState.registrationSuccess) {
        if (registrationUiState.registrationSuccess) {
            onSuccess()
        }
    }

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
                text = "Sign Up",
                fontWeight = FontWeight.SemiBold,
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
                value = username,
                onValueChange = { username = it },
                hint = "Name",
                leadingIcon = Icons.Default.AccountCircle,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                keyboardType = KeyboardType.Text,
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

            Button(
                onClick = {
                    registrationViewModel.registerUser(username, password)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                enabled = username.isNotEmpty() && password.isNotEmpty() && !registrationUiState.isLoading
            ) {
                if (registrationUiState.isLoading) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text(
                        text = "Finish",
                        fontSize = 18.sp,
                        modifier = Modifier
                            .padding(vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(1.dp))

            registrationUiState.errorMessage?.let { errorMessage ->
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 14.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}