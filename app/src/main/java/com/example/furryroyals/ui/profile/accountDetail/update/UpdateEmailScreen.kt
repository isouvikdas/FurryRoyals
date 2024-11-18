package com.example.furryroyals.ui.profile.accountDetail.update

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.furryroyals.auth.presentation.component.OtpInputField
import com.example.furryroyals.auth.presentation.component.TextTextField
import com.example.furryroyals.core.presentation.util.isSmallScreenHeight
import com.example.furryroyals.ui.profile.accountDetail.AccountDetailViewModel
import com.example.furryroyals.ui.profile.accountDetail.EmailVerificationUiState
import com.example.furryroyals.core.presentation.util.rememberImeState

@Composable
fun UpdateEmailScreen(
    emailVerificationUiState: EmailVerificationUiState,
    savedEmail: String,
    accountDetailViewModel: AccountDetailViewModel,
    onOtpSent: () -> Unit
) {

    var email by remember { mutableStateOf(emailVerificationUiState.email) }
    val isImeVisible by rememberImeState()
    val isValidEmail = remember(email) {
        val gmailPattern = Regex("^[A-Za-z0-9._%+-]+@gmail\\.com\$")
        gmailPattern.matches(email) && !emailVerificationUiState.isLoading
    }

    LaunchedEffect(emailVerificationUiState.isOtpSent) {
        if (emailVerificationUiState.isOtpSent) {
            onOtpSent()
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top
            ) {

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 18.dp)
                        .size(26.dp)
                        .clickable { }
                )

                Text(
                    text = savedEmail,
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                        .align(Alignment.CenterVertically),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )

            }

            TextTextField(
                value = email,
                onValueChange = { email = it },
                textColor = Color.Black,
                hint = "Email",
                leadingIcon = Icons.Outlined.Email,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp)
            )

            emailVerificationUiState.errorMessage?.let {
                Text(text = it,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(5.dp)
                )
            }

            Spacer(modifier = Modifier.fillMaxHeight(if (isImeVisible) 0.3f else 0.8f))

            Button(
                onClick = {
                    accountDetailViewModel.sendOtpToEmail(email)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                enabled = isValidEmail
            ) {
                if (emailVerificationUiState.isLoading) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text(
                        text = "Send OTP",
                        fontSize = 18.sp,
                        modifier = Modifier
                            .padding(vertical = 4.dp)
                    )
                }
            }

        }

    }
}


@Composable
fun OtpDialog(
    modifier: Modifier = Modifier,
    accountDetailViewModel: AccountDetailViewModel,
    emailVerificationUiState: EmailVerificationUiState,
    onDismiss: () -> Unit,
    onSuccess: () -> Unit
) {

    val otp = rememberSaveable { mutableStateOf("") }

    LaunchedEffect(emailVerificationUiState.isEmailVerified) {
        if (emailVerificationUiState.isEmailVerified) {
            onSuccess()
        }
    }

    Dialog(onDismissRequest = { onDismiss() }) {
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(10.dp))
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxHeight(0.4f)
                    .fillMaxWidth()
                    .background(Color.White)
                    .shadow(elevation = 10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(modifier = Modifier.height(if (isSmallScreenHeight()) 3.dp else 6.dp))

                    Text(
                        text = "OTP Verification",
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally),
                        fontSize = 25.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(if (isSmallScreenHeight()) 6.dp else 18.dp))

                    Text(
                        text = "Please enter the 6-digit code sent to your email.",
                        fontSize = 14.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .align(Alignment.Start),
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(if (isSmallScreenHeight()) 6.dp else 18.dp))

                    OtpInputField(
                        otp = otp,
                        count = 6,
                        textColor = Color.Black,
                        otpBoxModifier = Modifier
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(8.dp)
                            )
                    )

                    Spacer(modifier = Modifier.height(if (isSmallScreenHeight()) 5.dp else 10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(text = "Resend OTP", modifier = Modifier.clickable { })
                    }

                    Spacer(modifier = Modifier.height(if (isSmallScreenHeight()) 15.dp else 30.dp))

                    Button(
                        onClick = {
                            accountDetailViewModel.verifyEmail(otp.value)
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text(
                            text = "Verify OTP",
                            fontSize = 20.sp,
                            modifier = Modifier.padding(vertical = 1.dp)
                        )
                    }

                    emailVerificationUiState.errorMessage?.let {
                        Text(text = it,
                            fontSize = 15.sp,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(5.dp)
                        )
                    }
                }
            }
        }
    }
}
