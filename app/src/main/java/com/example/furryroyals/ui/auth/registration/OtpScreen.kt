package com.example.furryroyals.ui.auth.registration

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.border
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.furryroyals.ui.isSmallScreenHeight
import com.example.furryroyals.ui.rememberImeState
import com.example.furryroyals.ui.component.OtpInputField

@Composable
fun AnimatedOtpScreen(
    modifier: Modifier = Modifier,
    onSuccess: () -> Unit,
    registrationViewModel: RegistrationViewModel,
    onSignInClick: () -> Unit
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
            OtpScreen(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(if (isImeVisible) 0.82f else 0.5f),
                onSuccess = { onSuccess() },
                onSignInClick = onSignInClick,
                registrationViewModel = registrationViewModel

            )
        }
    }
}

@Composable
fun OtpScreen(
    modifier: Modifier = Modifier,
    registrationViewModel: RegistrationViewModel,
    onSignInClick: () -> Unit,
    onSuccess: () -> Unit
) {
    val registrationUiState = registrationViewModel.registrationUiState

    Surface(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)),
        color = Color.White,
        shadowElevation = 10.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isSmallScreenHeight()) {
                Spacer(modifier = Modifier.fillMaxSize(0.05f))
            } else {
                Spacer(modifier = Modifier.fillMaxSize(0.1f))
            }

            Text(
                text = "OTP Verification",
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                fontSize = 25.sp,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.padding(vertical = 10.dp))

            Text(
                text = "Please enter the 6-digit code sent to your number.",
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(horizontal = 20.dp),
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.padding(vertical = 10.dp))

            val otp = remember {
                mutableStateOf("")
            }

            OtpInputField(
                otp = otp,  // Use otpState to track the OTP input
                count = 6,  // Number of OTP boxes
                textColor = Color.Black,
                otpBoxModifier = Modifier
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(8.dp)
                    )
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "By proceeding, you agree to our Terms and Conditions and Privacy Policy.",
                    fontSize = 13.sp,
                    color = Color.Black
                )
            }

            Button(
                onClick = {
                    registrationViewModel.verifyOtp(phoneNumber = registrationUiState.phoneNumber, otp = otp.value)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                enabled = otp.value.length == 6 && !registrationUiState.isLoading
            ) {
                if (registrationUiState.isLoading) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text(
                        text = "Verify",
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
                    text = "Already have an account? ",
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Text(
                    text = "Sign In",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable { onSignInClick() }
                )
            }

            Spacer(modifier = Modifier.height(1.dp))

            // Display any error message
            registrationUiState.errorMessage?.let { errorMessage ->
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 14.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            // Display a success message if registration is successful
            if (registrationUiState.isOtpVerified) {
                onSuccess()
            }
        }
    }
}
