package com.example.chesstats.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chesstats.R
import com.example.chesstats.presentation.firstScreen.FirstPlayerScreen
import com.example.chesstats.presentation.firstScreen.FirstScreenViewModel
import com.example.chesstats.presentation.leaderboardScreen.LeaderBoardScreen
import com.example.chesstats.presentation.leaderboardScreen.LeaderBoardViewModel
import com.example.chesstats.ui.theme.ChesStatsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val firstScreenViewModel: FirstScreenViewModel by viewModels()
    private val leaderBoardViewModel: LeaderBoardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val controller = rememberNavController()
            ChesStatsTheme {

                Scaffold(
                    content = {
                        NavHost(
                            navController = controller,
                            startDestination = "FirstPlayerScreen",
                            modifier = Modifier.padding(it)
                        ) {

                            composable("FirstPlayerScreen") {
                                FirstPlayerScreen(firstScreenViewModel)
                            }

                            // Pantalla B
                            composable("LeaderBoardScreen") {
                                LeaderBoardScreen(leaderBoardViewModel)
                            }
                        }
                    },

                    bottomBar = {

                        var selected by remember {mutableIntStateOf(0)}

                        NavigationBar(
                            containerColor = (Color(0XFF606E79))
                        ) {
                            NavigationBarItem(selected = selected == 0,
                                colors = NavigationBarItemDefaults.colors(),
                                icon = {
                                    Image(painter = painterResource(R.drawable.blitz),
                                        contentDescription = "Icon")
                            }, onClick = {
                                controller.navigate("FirstPlayerScreen")
                                    selected = 0
                                })

                            NavigationBarItem(selected = selected == 1,
                                icon = {
                                    Image(painter = painterResource(R.drawable.bullet),
                                        contentDescription = "Icon")
                                }, onClick = {
                                    controller.navigate("LeaderBoardScreen")
                                    selected = 1
                                })

                            NavigationBarItem(selected = selected == 2,
                                icon = {
                                    Image(painter = painterResource(R.drawable.rapid),
                                        contentDescription = "Icon")
                                }, onClick = {
                                    selected = 2
                                })
                        }
                    })
            }
        }
    }
}
