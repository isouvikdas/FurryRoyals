package com.example.furryroyals.auth.registration

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.furryroyals.R
import com.example.furryroyals.ui.theme.FurryRoyalsTheme
import com.example.furryroyals.util.AuthTextField


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RegistrationScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Column {
            Image(
                painter = painterResource(id = R.drawable.pets),
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxWidth(0.25f)
                    .fillMaxHeight(0.27f)
            )

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "The best for your pets!",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 26.sp
                )
            }
            Text(
                text = "Register",
                fontWeight = FontWeight.SemiBold,
                fontSize = 26.sp
            )
        }

        AuthTextField(
            textFieldState = TextFieldState(),
            hint = "Name",
            leadingIcon = Icons.Outlined.AccountCircle,
            trailingIcon = Icons.Outlined.Check,
            keyboardType = KeyboardType.Phone,
            modifier = Modifier.fillMaxWidth()
        )

        AuthTextField(
            textFieldState = TextFieldState(),
            hint = "Password",
            isPassword = true,
            leadingIcon = Icons.Outlined.Lock,
            trailingText = "Forgot?",
            modifier = Modifier.fillMaxWidth()
        )

        Button(

            onClick = { /*TODO*/ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Register",
                fontSize = 17.sp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "Already have an account? ",
                fontSize = 16.sp,
            )
            Text(
                text = "Login",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { }
            )
        }

        Spacer(modifier = Modifier.height(1.dp))
    }
}

@Preview
@Composable
fun RegisterScreenPreview() {
    FurryRoyalsTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            RegistrationScreen(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}