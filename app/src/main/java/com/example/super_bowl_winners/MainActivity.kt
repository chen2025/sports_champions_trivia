package com.example.super_bowl_winners

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.super_bowl_winners.ui.theme.Super_bowl_winnersTheme


/*
Authored by Tony Chen and Jack D'Angelo
The app is an interactive trivia game focused on NBA and NFL champions. Users can select a sport and answer questions about which team won the championship in a given year.
The app features:
- Navigation: Allows users to move between different pages of the app.
- Trivia Questions: Generates random questions about NBA and NFL champions.
- User Interaction: Users can select answers, and the app keeps track of their score and time remaining.
- UI Components: Displays questions, possible answers, score, and time using Jetpack Compose.

The app provides an engaging way for users to test their knowledge of sports history.
The FirstPage composable function serves as the main entry point where users can choose between NFL and NBA trivia.
The ResultPage composable function displays the user's score and provides an option to replay the game.
 */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Super_bowl_winnersTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Navigate()
                }
            }
        }
    }
}

@Composable
fun Navigate(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "FirstPage") {
        composable(
            route = "FirstPage"
        ) {
            FirstPage(navController = navController)
        }

        composable(
            route = "SecondPage/{sport}",
            arguments = listOf(
                navArgument("sport") { type = NavType.StringType }
            )
        ) {
            val sport = it.arguments?.getString("sport")
            sport?.let {
                SecondPage(navController = navController, it)
            }
        }

        composable(
            route = "ResultPage/{score}",
            arguments = listOf(
                navArgument("score") { type = NavType.IntType }
            )
        ) {
            val userScore = it.arguments?.getInt("score")
            userScore?.let {
                ResultPage(navController, it)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NavigationPreview() {
    Super_bowl_winnersTheme {
        Navigate()
    }
}