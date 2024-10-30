package com.example.furryroyals.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.FurryRoyalsTheme

@Composable
fun ProfileScreen() {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            HorizontalDivider(modifier = Modifier)
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
                    text = "+916009181866",
                    color = Color.Black,
                    fontSize = 16.sp
                )
            }
            HorizontalDivider(modifier = Modifier.padding(bottom = 20.dp))

            ProfileTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .clickable { },
                leadingIcon = Icons.Outlined.FilterList,
                text = "My Orders",
                trailingIcon = Icons.AutoMirrored.Filled.ArrowForwardIos
            )

            ProfileTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .clickable { },
                leadingIcon = Icons.Outlined.LocationOn,
                text = "My Address",
                trailingIcon = Icons.AutoMirrored.Filled.ArrowForwardIos
            )

            ProfileTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .clickable { },
                leadingIcon = Icons.Outlined.AccountCircle,
                text = "Account Detail",
                trailingIcon = Icons.AutoMirrored.Filled.ArrowForwardIos
            )


            ProfileTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .clickable { },
                leadingIcon = Icons.Outlined.Logout,
                text = "Sign Out",
                trailingIcon = null
            )
        }
    }
}

@Composable
fun ProfileTextField(
    modifier: Modifier,
    leadingIcon: ImageVector,
    trailingIcon: ImageVector?,
    text: String
) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.width(18.dp))
            Image(
                imageVector = leadingIcon,
                contentDescription = null,
                modifier = Modifier.size(28.dp)
            )

            Text(
                text = text,
                fontSize = 17.sp,
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            trailingIcon?.let {
                Image(
                    imageVector = it,
                    contentDescription = null,
                    modifier = Modifier.size(17.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }

            Spacer(modifier = Modifier.width(18.dp))

        }

        HorizontalDivider(modifier = Modifier.padding(horizontal = 18.dp, vertical = 2.dp))

    }

}


@Preview
@Composable
fun ProfileScreenPreview() {
    FurryRoyalsTheme {
        ProfileScreen()
    }
}