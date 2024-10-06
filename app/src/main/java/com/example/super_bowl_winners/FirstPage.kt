package com.example.super_bowl_winners

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirstPage(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sports Champions Trivia", fontSize = 20.sp) },
                colors = topAppBarColors(
                    containerColor = colorResource(id = R.color.teal_200),
                    titleContentColor = Color.White,
                )
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        navController.navigate("SecondPage/nfl")
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.teal_200)
                    ),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.size(250.dp, 100.dp)
                ) {
                    Text(
                        "NFL",
                        color = Color.White,
                        fontSize = 24.sp
                    )
                }

                Button(
                    onClick = {
                        navController.navigate("SecondPage/nba")
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.teal_200)
                    ),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.size(250.dp, 100.dp)
                ) {
                    Text(
                        "NBA",
                        color = Color.White,
                        fontSize = 24.sp
                    )
                }
            }
        }
    )
}