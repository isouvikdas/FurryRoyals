package com.example.furryroyals.ui.profile

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
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
import com.example.furryroyals.ui.component.ProfileTextField
import com.example.furryroyals.ui.isSmallScreenHeight

@Composable
fun ProfileScreen(
    profileUiState: ProfileUiState,
    onAccountDetailClick: () -> Unit,
    onAddressClick: () -> Unit,
    onOrdersClick: () -> Unit
//    onSignOutClick: () -> Unit
) {
    val phoneNumber by rememberSaveable { mutableStateOf(profileUiState.phoneNumber) }

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
//                        onSignOutClick()
                               },
                leadingIcon = Icons.Outlined.Logout,
                text = "Sign Out",
                trailingIcon = null
            )
        }
    }
}


@Composable
fun AnimatedSignOutDialogue(
    modifier: Modifier = Modifier,
    profileViewModel: ProfileViewModel,
    profileUiState: ProfileUiState,
    onSignOutSuccess: () -> Unit,
    onCancelClick: () -> Unit

) {
    var visible by remember { mutableStateOf(false) }

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
            SignOutDialogue(
                modifier = Modifier.fillMaxHeight(0.35f),
                onSignOutSuccess = { onSignOutSuccess() },
                onCancelClick = { onCancelClick() },
                profileViewModel = profileViewModel,
                profileUiState = profileUiState
            )

        }
    }
}


@Composable
fun SignOutDialogue(
    modifier: Modifier = Modifier,
    onSignOutSuccess: () -> Unit,
    onCancelClick: () -> Unit,
    profileViewModel: ProfileViewModel,
    profileUiState: ProfileUiState
) {

    LaunchedEffect(profileUiState.logOutSuccess) {
        if (profileUiState.logOutSuccess) {
            onSignOutSuccess()
        }
    }

    Surface(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
        color = Color.White,
        shadowElevation = 10.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            if (isSmallScreenHeight()) {
                Spacer(modifier = Modifier.fillMaxSize(0.02f))
            } else {
                Spacer(modifier = Modifier.fillMaxSize(0.05f))
            }

            Text(
                text = "Come back soon!",
                modifier = Modifier
                    .padding(horizontal = 18.dp)
                    .align(Alignment.Start),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )

            if (isSmallScreenHeight()) {
                Spacer(modifier = Modifier.fillMaxSize(0.017f))
            } else {
                Spacer(modifier = Modifier.fillMaxSize(0.03f))
            }

            Text(
                text = "Are you sure you want to sign out?",
                modifier = Modifier
                    .padding(horizontal = 18.dp)
                    .align(Alignment.Start),
                fontSize = 16.sp
            )

            if (isSmallScreenHeight()) {
                Spacer(modifier = Modifier.fillMaxSize(0.025f))
            } else {
                Spacer(modifier = Modifier.fillMaxSize(0.06f))
            }

            Button(
                onClick = {
                    profileViewModel.logOut()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = "Sign Out",
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(vertical = 3.dp)
                )
            }

            Text(
                text = "Cancel",
                fontSize = 20.sp,
                color = Color.Red,
                modifier = Modifier.clickable { onCancelClick() })

        }
    }
}


