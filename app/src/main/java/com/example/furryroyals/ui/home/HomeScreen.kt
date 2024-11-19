package com.example.furryroyals.ui.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.compose.FurryRoyalsTheme

@Composable
fun HomeScreen(navController : NavHostController) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color.White
    ) {

        var number by rememberSaveable { mutableIntStateOf(0) }
        Log.i("number", number.toString())


        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = number.toString(), fontSize = 20.sp)
            Button(
                onClick = {number += 1},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = "Increment",
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(vertical = 4.dp),
                )

            }
        }


    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    FurryRoyalsTheme {
        HomeScreen(navController = rememberNavController())
    }
}