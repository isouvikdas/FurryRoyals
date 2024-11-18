package com.example.furryroyals.ui.profile.signout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.compose.FurryRoyalsTheme
import com.example.furryroyals.core.presentation.util.isSmallScreenHeight


@Composable
fun SignOutDialog(
    modifier: Modifier = Modifier,
    onSignOutConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(10.dp))
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxHeight(0.3f)
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
                        text = "Come back soon!",
                        modifier = Modifier
                            .align(Alignment.Start),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(if (isSmallScreenHeight()) 6.dp else 12.dp))

                    Text(
                        text = "Are you sure you want to sign out?",
                        modifier = Modifier
                            .align(Alignment.Start),
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.height(if (isSmallScreenHeight()) 20.dp else 40.dp))

                    Button(
                        onClick = {
                            onSignOutConfirm()
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text(
                            text = "Sign Out",
                            fontSize = 20.sp,
                            modifier = Modifier.padding(vertical = 1.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Cancel",
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .padding(vertical = 5.dp)
                            .clickable { onDismiss() }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun SignOutDialogPreview() {
    FurryRoyalsTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) {
            SignOutDialog(onSignOutConfirm = { /*TODO*/ }, modifier = Modifier.padding(it)) {

            }
        }
    }
}

