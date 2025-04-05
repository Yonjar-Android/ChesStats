package com.example.chesstats.presentation.leaderboardScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chesstats.FakeRepository
import com.example.chesstats.presentation.detailPlayerScreen.DetailPlayerScreen
import com.example.chesstats.presentation.detailPlayerScreen.DetailPlayerViewModel
import com.example.chesstats.presentation.extras.DetailPlayerScreenNav
import com.example.chesstats.presentation.extras.LeaderBoardScreenNav
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalSharedTransitionApi::class)

class LeaderBoardScreenKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    val viewModel = LeaderBoardViewModel(FakeRepository())

    val detailViewModel = DetailPlayerViewModel(FakeRepository())

    @Test
    fun screenChargeCorrectlyAndNavigateToDetail() {
        composeTestRule.setContent {
            val controller = rememberNavController()
            // ← Simula insets
            NavHost(
                navController = controller,
                startDestination = LeaderBoardScreenNav,
                modifier = Modifier.windowInsetsPadding(insets = WindowInsets.statusBars)
            ) {
                composable<LeaderBoardScreenNav> {
                    SharedTransitionLayout {
                        AnimatedVisibility(
                            visible = true,
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                            Box(Modifier.windowInsetsPadding(WindowInsets.statusBars)) {
                                LeaderBoardScreen(
                                    viewModel,
                                    sharedTransitionScope = this@SharedTransitionLayout,
                                    animatedVisibilityScope = this@AnimatedVisibility,
                                    controller = controller
                                )
                            }
                        }
                    }
                }
                composable<DetailPlayerScreenNav> {
                    SharedTransitionLayout {
                        AnimatedVisibility(
                            visible = true,
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                            DetailPlayerScreen(
                                username = "Yonjar",
                                detailPlayerViewModel = detailViewModel,
                                controller = controller,
                                sharedTransitionScope = this@SharedTransitionLayout,
                                animatedVisibilityScope = this@AnimatedVisibility
                            )
                        }
                    }
                }
            }
        }
        composeTestRule.onNodeWithTag("LeaderBoardScreen")
            .assertExists("No cargó la pantalla leaderBoards")
        composeTestRule.onNodeWithTag("leaderBoardScreen")
            .assertExists("No cargó la lista de jugadores")
        composeTestRule.waitUntil {
            composeTestRule.onAllNodesWithTag("playerProfile1").fetchSemanticsNodes()
                .isNotEmpty()
        }
        composeTestRule.onNodeWithTag("playerProfile1")
            .assertExists("No cargó el jugador rankeado número1")
        composeTestRule.onNodeWithTag("playerProfile1").performClick()
        composeTestRule.onNodeWithTag("DetailPlayerScreen")
            .assertExists("No cargó el detalle del jugador")
    }

    @Test
    fun navigateThroughTabRow() {
        composeTestRule.setContent {
            val controller = rememberNavController()
            // ← Simula insets
            NavHost(
                navController = controller,
                startDestination = LeaderBoardScreenNav,
                modifier = Modifier.windowInsetsPadding(insets = WindowInsets.statusBars)
            ) {
                composable<LeaderBoardScreenNav> {
                    SharedTransitionLayout {
                        AnimatedVisibility(
                            visible = true,
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                                LeaderBoardScreen(
                                    viewModel,
                                    sharedTransitionScope = this@SharedTransitionLayout,
                                    animatedVisibilityScope = this@AnimatedVisibility,
                                    controller = controller
                                )
                        }
                    }
                }
            }
        }

        composeTestRule.onNodeWithTag("LeaderBoardScreen")
            .assertExists("No cargó la pantalla leaderBoards")
        composeTestRule.onNodeWithTag("leaderBoardScreen")
            .assertExists("No cargó la lista de jugadores")
        composeTestRule.onNodeWithTag("tabRowLeaderboard")
            .assertExists("No cargó el tab row")
        composeTestRule.onNodeWithTag("tab1").assertExists("No cargó el tab para navegar").performClick()
        composeTestRule.onNodeWithTag("tab2").assertExists("No cargó el tab para navegar").performClick()
        composeTestRule.onNodeWithTag("tab0").assertExists("No cargó el tab para navegar").performClick()
    }
}