package com.example.furryroyals.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.furryroyals.ui.theme.FurryRoyalsTheme


@Composable
fun TextTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    textColor: Color,
    hint: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    leadingIcon: ImageVector,
) {

    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            onValueChange(newValue)
        },
        textStyle = LocalTextStyle.current.copy(
            color = textColor
        ),
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        modifier = modifier,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        label = { Text(text = hint) },
        leadingIcon = {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null,
                modifier = Modifier.padding(8.dp)
            )
        }
    )
}

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    value: String,
    hint: String,
    textColor: Color
) {
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            onValueChange(newValue)
        },
        textStyle = LocalTextStyle.current.copy(
            color = textColor
        ),
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        modifier = modifier,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        label = { Text(text = hint) },

        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Lock,
                contentDescription = null,
                modifier = Modifier.padding(8.dp)
            )
        },
        trailingIcon = {
            val image = if (passwordVisible) {
                Icons.Default.Visibility
            } else {
                Icons.Default.VisibilityOff
            }
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(
                    imageVector = image,
                    contentDescription = if (passwordVisible) "Hide password" else "Show password",
                    modifier = Modifier.padding(8.dp)
                )
            }
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
    )
}

@Composable
fun OTPTextField(
    modifier: Modifier = Modifier,
    length: Int = 6,
    onOtpEntered: (String) -> Unit
) {
    var otpCode by remember { mutableStateOf("") }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        repeat(length) { index ->
            OutlinedTextField(
                value = otpCode.getOrNull(index)?.toString() ?: "",
                onValueChange = { newValue ->
                    if (newValue.length <= 1 && otpCode.length < length) {
                        otpCode += newValue
                    }
                    onOtpEntered(otpCode)
                },
                singleLine = true,
                textStyle = TextStyle(fontSize = 20.sp, textAlign = TextAlign.Center),
                modifier = Modifier
                    .width(43.dp)
                    .height(46.dp)
                    .padding(4.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
    }
}



@Preview
@Composable
fun TextFieldPreview() {
    FurryRoyalsTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            OTPTextField(
                modifier = Modifier.padding(innerPadding),
                onOtpEntered = { "123456" }
            )
        }
    }
}
//
//@Preview
//@Composable
//fun PasswordPreview() {
//    FurryRoyalsTheme {
//        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//            var text by remember { mutableStateOf("") }
//            PasswordTextField(
//                modifier = Modifier.padding(innerPadding),
//                value = text,
//                hint = "password"
//            )
//        }
//    }
//}

