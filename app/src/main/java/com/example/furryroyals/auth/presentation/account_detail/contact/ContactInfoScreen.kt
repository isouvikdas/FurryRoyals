package com.example.furryroyals.auth.presentation.account_detail.contact

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.ArrowForwardIos
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.PhoneIphone
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.furryroyals.ui.theme.FurryRoyalsTheme

@Composable
fun ContactInfoScreen(
    email: String,
    phoneNumber: String,
    onEmailClick: () -> Unit
) {
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
                    .padding(top = 15.dp, bottom = 10.dp),
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
                    text = "Contact Information",
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                        .align(Alignment.CenterVertically),
                    style = MaterialTheme.typography.headlineMedium
                )
            }

            Text(
                text = "Manage your mobile numbers and email addresses to make sure your contact info is accurate and up to date.",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
//                    .padding(start = 13.dp, end = 13.dp)
            )

            Box(modifier = Modifier.padding(20.dp)) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clip(RoundedCornerShape(20.dp)),
                    color = MaterialTheme.colorScheme.tertiaryContainer,
                    shadowElevation = 20.dp
                ) {
                    Column(
                        modifier = Modifier
                            .padding(vertical = 10.dp)
                            .wrapContentHeight()
                    ) {
                        ContactInfoSection(
                            text = email,
                            modifier = Modifier
                                .padding(bottom = 7.5.dp)
                                .clickable { onEmailClick() },
                            leadingIcon = Icons.Outlined.Email
                        )

                        ContactInfoSection(
                            text = "+91$phoneNumber",
                            modifier = Modifier
                                .padding(top = 7.5.dp, bottom = 5.dp),
                            leadingIcon = Icons.Outlined.PhoneIphone
                        )
                    }
                }
            }


        }
    }
}

@Composable
fun ContactInfoSection(
    modifier: Modifier = Modifier,
    text: String? = null,
    leadingIcon: ImageVector? = null
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {

            leadingIcon?.let {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(start = 15.dp)
                )
            }


            text?.let {
                Text(
                    text = text,
                    modifier = Modifier
                        .padding(start = 10.dp),
                    style = MaterialTheme.typography.headlineSmall
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                imageVector = Icons.AutoMirrored.Outlined.ArrowForwardIos,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )

            Spacer(modifier = Modifier.width(13.dp))
        }
    }
}

@Preview
@Composable
fun ContactInfoPreview() {
    FurryRoyalsTheme {
        ContactInfoScreen(
            "souvikdas2412@gmail.com", "6009181866", {}
        )
    }
}