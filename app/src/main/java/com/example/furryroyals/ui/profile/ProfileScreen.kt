package com.example.furryroyals.ui.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.furryroyals.auth.presentation.component.ProfileTextField
import com.example.furryroyals.auth.presentation.registration.AnimatedRegisterScreen
import com.example.furryroyals.auth.presentation.registration.RegistrationUiState
import com.example.furryroyals.auth.presentation.registration.RegistrationViewModel

@Composable
fun ProfileScreen(
    phoneNumber: String,
    onAccountDetailClick: () -> Unit,
    onAddressClick: () -> Unit,
    onOrdersClick: () -> Unit,
    onSignOutClick: () -> Unit,
) {
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
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

                Text(
                    text = "+91$phoneNumber",
                    color = Color.Black,
                    fontSize = 16.sp
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
                leadingIcon = Icons.Outlined.Logout,
                text = "Sign Out",
                trailingIcon = null
            )
        }
    }

}


