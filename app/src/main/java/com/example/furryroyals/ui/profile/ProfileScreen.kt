package com.example.furryroyals.ui.profile

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.furryroyals.auth.presentation.component.ProfileTextField
import com.example.furryroyals.auth.presentation.login.AnimatedLoginScreen
import com.example.furryroyals.auth.presentation.login.AnimatedOtpScreen
import com.example.furryroyals.auth.presentation.login.LoginViewModel
import timber.log.Timber

@Composable
fun ProfileScreen(
    phoneNumber: String,
    onAccountDetailClick: () -> Unit,
    onAddressClick: () -> Unit,
    onOrdersClick: () -> Unit,
    onSignOutClick: () -> Unit,
    isLoggedIn: Boolean,
    loginViewModel: LoginViewModel,
    onDismiss: () -> Unit
) {
    val loginUiState by loginViewModel.loginUiState.collectAsStateWithLifecycle()

    val currentScreen = when {
        !isLoggedIn && !loginUiState.isOtpSent -> "login"
        !isLoggedIn && loginUiState.isOtpSent -> "otp"
        loginUiState.registrationSuccess -> "success"
        else -> "profile"
    }

    when (currentScreen) {

        "success" -> {
            Timber.tag("toggle").i("success phonenumber: %s", loginUiState.phoneNumber)
            Timber.tag("toggle").i("success isotpsent: %s", loginUiState.isOtpSent)
            Timber.tag("toggle").i("success isotpverified: %s", loginUiState.isOtpVerified)
            Timber.tag("toggle").i("success registrationsuccess: %s", loginUiState.registrationSuccess)
            Timber.tag("toggle").i("success isLoggedIn: %s", isLoggedIn)
            onDismiss()

        }

        "login" -> {
            AnimatedLoginScreen(
                loginViewModel = loginViewModel,
                loginUiState = loginUiState,
                onDismiss = onDismiss
            )
            Log.i("toggle", "login phonenumber: ${loginUiState.phoneNumber}")
            Log.i("toggle", "login isotpsent: ${loginUiState.isOtpSent}")
            Log.i("toggle", "login isotpverified: ${loginUiState.isOtpVerified}")
            Log.i("toggle", "login registrationsuccess: ${loginUiState.registrationSuccess}")
            Log.i("toggle", "login isLoggedIn: ${isLoggedIn}")

        }

        "otp" -> {
            AnimatedOtpScreen(
                loginViewModel = loginViewModel,
                loginUiState = loginUiState,
                onDismiss = onDismiss
            )
            Log.i("toggle", "otp phonenumber: ${loginUiState.phoneNumber}")
            Log.i("toggle", "otp isotpsent: ${loginUiState.isOtpSent}")
            Log.i("toggle", "otp isotpverified: ${loginUiState.isOtpVerified}")
            Log.i("toggle", "otp registrationsuccess: ${loginUiState.registrationSuccess}")
            Log.i("toggle", "otp isLoggedIn: ${isLoggedIn}")
        }

        "profile" -> {
            Surface(
                modifier = Modifier
                    .fillMaxSize(),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 18.dp, vertical = 18.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Account",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(
                            text = "+91$phoneNumber",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    HorizontalDivider(modifier = Modifier.padding(bottom = 20.dp))

                    ProfileTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp)
                            .clickable { onOrdersClick() },
                        leadingIcon = Icons.Outlined.FilterList,
                        text = "My Orders",
                        trailingIcon = Icons.AutoMirrored.Filled.ArrowForwardIos
                    )

                    ProfileTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp)
                            .clickable { onAddressClick() },
                        leadingIcon = Icons.Outlined.LocationOn,
                        text = "My Address",
                        trailingIcon = Icons.AutoMirrored.Filled.ArrowForwardIos
                    )

                    ProfileTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp)
                            .clickable { onAccountDetailClick() },
                        leadingIcon = Icons.Outlined.AccountCircle,
                        text = "Account Detail",
                        trailingIcon = Icons.AutoMirrored.Filled.ArrowForwardIos
                    )


                    ProfileTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp)
                            .clickable {
                                onSignOutClick()
                            },
                        leadingIcon = Icons.AutoMirrored.Outlined.Logout,
                        text = "Sign Out",
                        trailingIcon = null
                    )
                }
            }
        }

    }


}


