package com.example.super_bowl_winners

import android.os.CountDownTimer
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.font.FontWeight
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
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = when (sport) {
                                "nba" -> "NBA Trivia"
                                else -> "NFL Trivia"
                            },
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }
                },
                colors = topAppBarColors(
                    containerColor = colorResource(id = R.color.purple_200),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },

        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(horizontal = 16.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ScoreTimeDisplay(label = "Score", value = score.toString())
                    ScoreTimeDisplay(label = "Time", value = remainingTime)
                }

                Spacer(modifier = Modifier.height(30.dp))

                TextForQuestions(text = myQuestion)
                Spacer(modifier = Modifier.height(15.dp))

                answerArray.forEach { answer ->
                    AnswerButton(
                        answerText = answer,
                        onClick = { myAnswer = answer },
                        isSelected = myAnswer == answer,
                    )
                }

                Spacer(modifier = Modifier.height(50.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ButtonOkNext(
                        buttonText = "OK",
                        myOnClick = {
                            myQuestion = if (myAnswer == correctAnswer) {
                                score += 10
                                "Correct!"
                            } else {
                                "Wrong!"
                            }
                            nextEnabled = true
                        },
                        isEnabled = !nextEnabled && myAnswer.isNotEmpty()
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
fun ScoreTimeDisplay(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, fontSize = 18.sp, color = Color.Black)
        Text(text = value, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
    }
}

@Composable
fun AnswerButton(answerText: String, onClick: () -> Unit, isSelected: Boolean) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) colorResource(id = R.color.purple_200) else Color.LightGray
        ),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(2.dp, color = colorResource(id = R.color.purple_200)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .height(60.dp)
    ) {
        Text(
            text = answerText,
            fontSize = 20.sp,
            color = if (isSelected) Color.White else Color.Black
        )
    }
}

@Composable
fun TextForQuestions(text: String) {
    Text(
        text = text,
        fontSize = 22.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color.White,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .background(
                color = colorResource(id = R.color.purple_200),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(16.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    )
}

@Composable
fun ButtonOkNext(buttonText: String, myOnClick: () -> Unit, isEnabled: Boolean) {
    Button(
        onClick = myOnClick,
        enabled = isEnabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.purple_200),
            disabledContainerColor = Color.LightGray
        ),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(2.dp, color = colorResource(id = R.color.purple_200)),
        modifier = Modifier
            .width(150.dp)
            .height(60.dp)
    ) {
        Text(buttonText, fontSize = 20.sp, color = Color.White)
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
