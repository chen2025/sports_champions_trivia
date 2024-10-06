package com.example.super_bowl_winners

import android.os.CountDownTimer
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlin.random.Random


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondPage(navController: NavController, sport: String) {
    var score by remember { mutableStateOf(0) }
    var remainingTime by remember { mutableStateOf("30") }
    var myQuestion by remember { mutableStateOf("") }
    var myAnswer by remember { mutableStateOf("") }
    var correctAnswer by remember { mutableStateOf("") }
    var answerArray by remember { mutableStateOf(arrayOf("")) }
    var nextEnabled by remember { mutableStateOf(false) }

    val timer by remember {
        mutableStateOf(
            object : CountDownTimer(30000L, 1000L) {
                override fun onTick(p0: Long) {
                    remainingTime = (p0 / 1000L).toString()
                }

                override fun onFinish() {
                    navController.navigate("ResultPage/$score")
                }
            }.start()
        )
    }

    // LaunchEffect -> enter the composition; when you first enter the composition
    // SideEffect -> each recomposition; every time
    // DisposableEffect -> when you leave the composition
    LaunchedEffect(key1 = "Sport") {
        val resultList = generateQuestions(sport)
        myQuestion = resultList[0].toString()
        correctAnswer = resultList[1].toString()
        answerArray = resultList[2] as Array<String>
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Arrow Back"
                        )
                    }
                },
                title = {
                    Text(
                        text = when (sport) {
                            "nba" -> "NBA"
                            else -> "NFL"
                        },
                        fontSize = 20.sp
                    )
                },
                colors = topAppBarColors(
                    containerColor = colorResource(id = R.color.purple_200),
                    navigationIconContentColor = Color.White,
                    titleContentColor = Color.White,
                )
            )
        },

        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text("Score:  ", fontSize = 16.sp, color = Color.Black)
                    Text(score.toString(), fontSize = 16.sp, color = Color.Black)
                    Text("Remaining Time", fontSize = 16.sp, color = Color.Black)
                    Text(remainingTime, fontSize = 16.sp, color = Color.Black)
                }
                Spacer(modifier = Modifier.height(30.dp))

                TextForQuestions(text = myQuestion)
                Spacer(modifier = Modifier.height(15.dp))

                answerArray.forEach {
                    Button(
                        onClick = {
                            myAnswer = it
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.purple_200)
                        ),
                        shape = RoundedCornerShape(5.dp),
                        border = BorderStroke(2.dp, color = colorResource(id = R.color.purple_200)),
                        modifier = Modifier.size(300.dp, 75.dp)
                    ) {
                        Text(it, fontSize = 24.sp, color = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(50.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ButtonOkNext(
                        buttonText = "OK",
                        myOnClick = {
                            myQuestion = "Wrong!"
                            nextEnabled = true

                            if (myAnswer == correctAnswer) {
                                score += 10
                                myQuestion = "Correct"
                                myAnswer = ""
                            }
                        },
                        isEnabled = myAnswer.isNotEmpty()
                    )

                    ButtonOkNext(
                        buttonText = "Next",
                        myOnClick = {
                            val newResultList = generateQuestions(sport)
                            myQuestion = newResultList[0].toString()
                            correctAnswer = newResultList[1].toString()
                            answerArray = newResultList[2] as Array<String>
                            myAnswer = ""
                            nextEnabled = false
                        },
                        isEnabled = nextEnabled
                    )
                }
            }
        }
    )
}

@Composable
fun TextForQuestions(text: String) {
    Text(
        text,
        fontSize = 24.sp,
        color = Color.White,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .background(color = colorResource(id = R.color.purple_200))
            .size(300.dp, 75.dp)
            .wrapContentHeight()
    )
}

@Composable
fun ButtonOkNext(buttonText: String, myOnClick: () -> Unit, isEnabled: Boolean) {
    Button(
        onClick = myOnClick,
        enabled = isEnabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(5.dp),
        border = BorderStroke(2.dp, color = colorResource(id = R.color.purple_200)),
        modifier = Modifier.width(150.dp)

    ) {
        Text(buttonText, fontSize = 24.sp, color = colorResource(id = R.color.purple_200))
    }
}

fun generateQuestions(sport: String): ArrayList<Any> {
    var year = Random.nextInt(1967, 2024)
    var textQuestion = "Who won the Super Bowl in $year?"
    var (correctAnswer, answerArray) = fetchSuperBowlChampion(year)

    if (sport == "nba") {
        year = Random.nextInt(1977, 2024)
        textQuestion = "Who won the NBA Championship in $year?"
        val nbaChampion = fetchNBAChampion(year)
        correctAnswer = nbaChampion.first
        answerArray = nbaChampion.second
    }
    return arrayListOf(textQuestion, correctAnswer, answerArray)
}