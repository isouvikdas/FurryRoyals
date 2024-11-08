package com.example.furryroyals.ui.profile.accountDetail

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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.FurryRoyalsTheme
import com.example.furryroyals.R

@Composable
fun AccountDetailScreen(
    onNameClick: () -> Unit,
    onContactClick: () -> Unit,
    username: String,
    email: String,
    phoneNumber: String
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
                    text = "Account Detail",
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                        .align(Alignment.CenterVertically),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Text(
                text = "Furryroyals uses this information to verify your identify and to keep our community safe.",
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                fontWeight = FontWeight.W600
            )

            Box(modifier = Modifier.padding(20.dp)) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clip(RoundedCornerShape(20.dp)),
                    color = MaterialTheme.colorScheme.primary,
                    shadowElevation = 20.dp
                ) {
                    Column(
                        modifier = Modifier
                            .padding(vertical = 10.dp)
                            .wrapContentHeight()
                    ) {
                        InfoSection(
                            heading = "Contact info",
                            text = email,
                            phoneNumber = phoneNumber,
                            modifier = Modifier
                                .padding(bottom = 7.5.dp)
                                .clickable { onContactClick() }
                        )

                        InfoSection(
                            heading = "Name",
                            text = username,
                            modifier = Modifier
                                .padding(top = 7.5.dp, bottom = 5.dp)
                                .clickable { onNameClick() }
                        )
                    }
                }
            }

        }
    }
}

@Composable
fun InfoSection(
    modifier: Modifier = Modifier,
    heading: String,
    text: String? = null,
    phoneNumber: String? = null
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Text(
            text = heading,
            fontSize = 18.sp,
            modifier = Modifier.padding(start = 15.dp),
            fontWeight = FontWeight.W600,
            fontFamily = FontFamily(Font(R.font.inter_variablefont_opsz_wght))
        )

        Row(modifier = Modifier.fillMaxWidth()) {

            text?.let {
                Text(
                    text = text,
                    modifier = Modifier
                        .padding(horizontal = 15.dp),
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.inter_variablefont_opsz_wght))
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                imageVector = Icons.AutoMirrored.Outlined.ArrowForwardIos,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )

            Spacer(modifier = Modifier.width(14.dp))
        }

        phoneNumber?.let {
            Text(
                text = it,
                modifier = Modifier.padding(start = 15.dp),
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.inter_variablefont_opsz_wght))
            )

        }


    }
}


@Preview
@Composable
fun AccountDetailScreenPreview() {
    FurryRoyalsTheme {
        AccountDetailScreen({}, {}, "Souvik", "souvikdas2412@gmail.com", "+916009181866"
        )
    }
}