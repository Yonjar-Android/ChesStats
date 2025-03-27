@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.example.chesstats.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
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
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chesstats.R
import com.example.chesstats.presentation.chessStreamersScreen.ChessStreamersScreen
import com.example.chesstats.presentation.chessStreamersScreen.ChessStreamersViewModel
import com.example.chesstats.presentation.detailPlayerScreen.DetailPlayerScreen
import com.example.chesstats.presentation.detailPlayerScreen.DetailPlayerViewModel
import com.example.chesstats.presentation.firstScreen.FirstPlayerScreen
import com.example.chesstats.presentation.firstScreen.FirstScreenViewModel
import com.example.chesstats.presentation.leaderboardScreen.LeaderBoardScreen
import com.example.chesstats.presentation.leaderboardScreen.LeaderBoardViewModel
import com.example.chesstats.ui.theme.ChesStatsTheme
import com.example.chesstats.utils.NavAnimations
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val firstScreenViewModel: FirstScreenViewModel by viewModels()
    private val leaderBoardViewModel: LeaderBoardViewModel by viewModels()
    private val detailPlayerViewModel: DetailPlayerViewModel by viewModels()
    private val chessStreamersViewModel: ChessStreamersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val controller = rememberNavController()
            ChesStatsTheme {
                Scaffold(
                    bottomBar = {
                        var selected by remember { mutableIntStateOf(0) }

                        NavigationBar(
                            containerColor = (Color(0XFF172734))
                        ) {
                            NavigationBarItem(
                                selected = selected == 0,
                                colors = NavigationBarItemDefaults.colors(
                                    indicatorColor = Color(0XFF171434)
                                ),
                                icon = {
                                    Icon(
                                        imageVector = Icons.Default.Search,
                                        contentDescription = "Icon",
                                        modifier = Modifier.size(40.dp),
                                        tint = Color.White
                                    )
                                }, onClick = {
                                    controller.navigate("FirstPlayerScreen")
                                    selected = 0
                                })

                            NavigationBarItem(
                                selected = selected == 1,
                                colors = NavigationBarItemDefaults.colors(
                                    indicatorColor = Color(0XFF171434)
                                ),
                                icon = {
                                    Icon(
                                        painter = painterResource(R.drawable.ranking),
                                        contentDescription = "Icon",
                                        modifier = Modifier.size(40.dp),
                                        tint = Color.White
                                    )
                                }, onClick = {
                                    controller.navigate("LeaderBoardScreen")
                                    selected = 1
                                })

                            NavigationBarItem(
                                selected = selected == 2,
                                colors = NavigationBarItemDefaults.colors(
                                    indicatorColor = Color(0XFF171434)
                                ),
                                icon = {
                                    Image(
                                        painter = painterResource(R.drawable.rapid),
                                        contentDescription = "Icon", modifier = Modifier.size(40.dp)
                                    )
                                }, onClick = {
                                    controller.navigate("ChessStreamersScreen")
                                    selected = 2
                                })
                        }
                    }) { innerPadding ->

                    SharedTransitionLayout(modifier = Modifier.padding(innerPadding)) {
                        NavHost(
                            navController = controller,
                            startDestination = "FirstPlayerScreen",
                            exitTransition = { NavAnimations.exitAnimation() },
                            enterTransition = { NavAnimations.enterAnimation() },
                            popExitTransition = { NavAnimations.popExitAnimation() },
                            popEnterTransition = { NavAnimations.popEnterAnimation() }
                        ) {

                            composable("FirstPlayerScreen") {
                                FirstPlayerScreen(firstScreenViewModel)
                            }

                            // Pantalla B
                            composable("LeaderBoardScreen") {
                                LeaderBoardScreen(leaderBoardViewModel, controller, this)
                            }

                            composable("DetailPlayerScreen/{username}") { backstackEntry ->
                                val username =
                                    backstackEntry.arguments?.getString("username") ?: ""
                                DetailPlayerScreen(
                                    username,
                                    controller = controller,
                                    detailPlayerViewModel = detailPlayerViewModel,
                                    animatedVisibilityScope = this
                                )
                            }

                            composable("ChessStreamersScreen") {
                                ChessStreamersScreen(chessStreamersViewModel)
                            }
                        }
                    }
                }
            }
        }
    }
}
