package com.example.furryroyals.ui.profile.accountDetail.update

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Button
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.furryroyals.ui.component.TextTextField
import com.example.furryroyals.ui.profile.accountDetail.AccountDetailUiState
import com.example.furryroyals.ui.profile.accountDetail.AccountDetailViewModel
import com.example.furryroyals.ui.rememberImeState

@Composable
fun UpdateNameScreen(
    accountDetailUiState: AccountDetailUiState,
    accountDetailViewModel: AccountDetailViewModel
) {

    val isImeVisible by rememberImeState()
    var name by remember {
        mutableStateOf(accountDetailUiState.username)
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
                    text = accountDetailUiState.username,
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                        .align(Alignment.CenterVertically),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )

            }

            TextTextField(
                value = name,
                onValueChange = { name= it },
                textColor = Color.Black,
                hint = "Name",
                leadingIcon = Icons.Outlined.AccountCircle,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp)
            )

            accountDetailUiState.errorMessage?.let {
                Text(text = it,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(5.dp)
                )
            }

            Spacer(modifier = Modifier.fillMaxHeight(if (isImeVisible) 0.3f else 0.8f))

            Button(
                onClick = {
                    accountDetailViewModel.updateUsername(name)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                enabled = name.length >= 2 && name.isNotEmpty()
            ) {
                if (accountDetailUiState.isLoading) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text(
                        text = "Save",
                        fontSize = 18.sp,
                        modifier = Modifier
                            .padding(vertical = 4.dp)
                    )
                }
            }

        }

    }
}