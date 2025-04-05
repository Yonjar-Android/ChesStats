package com.example.chesstats.presentation.chessStreamersScreen

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
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chesstats.FakeRepository
import com.example.chesstats.presentation.detailPlayerScreen.DetailPlayerScreen
import com.example.chesstats.presentation.detailPlayerScreen.DetailPlayerViewModel
import com.example.chesstats.presentation.extras.ChessStreamersScreenNav
import com.example.chesstats.presentation.extras.DetailPlayerScreenNav
import org.junit.Rule
import org.junit.Test


@OptIn(ExperimentalSharedTransitionApi::class)
class ChessStreamersScreenKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    val viewModel = ChessStreamersViewModel(FakeRepository())

    val detailViewModel = DetailPlayerViewModel(FakeRepository())

    @Test
    fun streamersScreenChargeCorrectly() {
        composeTestRule.setContent {
            val controller = rememberNavController()
            // ← Simula insets
            NavHost(
                navController = controller,
                startDestination = ChessStreamersScreenNav,
                modifier = Modifier.windowInsetsPadding(insets = WindowInsets.statusBars)
            ) {
                composable<ChessStreamersScreenNav> {
                    SharedTransitionLayout {
                        AnimatedVisibility(
                            visible = true,
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                                ChessStreamersScreen(
                                    viewModel,
                                    sharedTransitionScope = this@SharedTransitionLayout,
                                    animatedVisibilityScope = this@AnimatedVisibility,
                                    controller = controller
                                )
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
        composeTestRule.onNodeWithTag("ChessStreamersScreen")
            .assertExists("No cargó la pantalla de streamers")
        composeTestRule.onNodeWithTag("streamersList")
            .assertExists("No cargó la lista de streamers")
        composeTestRule.onNodeWithTag("streamerAnnaCramling")
            .assertExists("No cargó el perfil de AnnaCramling").performClick()
        composeTestRule.onNodeWithTag("DetailPlayerScreen")
            .assertExists("No cargó el detalle del streamer")
    }

    @Test
    fun clickOnTwitchIcon() {
        composeTestRule.setContent {
            val controller = rememberNavController()
            // ← Simula insets
            NavHost(
                navController = controller,
                startDestination = ChessStreamersScreenNav,
                modifier = Modifier.windowInsetsPadding(insets = WindowInsets.statusBars)
            ) {
                composable<ChessStreamersScreenNav> {
                    SharedTransitionLayout {
                        AnimatedVisibility(
                            visible = true,
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                                ChessStreamersScreen(
                                    viewModel,
                                    sharedTransitionScope = this@SharedTransitionLayout,
                                    animatedVisibilityScope = this@AnimatedVisibility,
                                    controller = controller
                                )
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
        composeTestRule.onNodeWithTag("ChessStreamersScreen")
            .assertExists("No cargó la pantalla de streamers")
        composeTestRule.onNodeWithTag("streamersList")
        composeTestRule.onNodeWithTag("twitchAnnaCramling")
            .assertExists("No cargó el ícono de twitch").performClick()
        composeTestRule.onNodeWithTag("AlertDialogChannel")
            .assertExists("No cargó el alert dialog para visitar el canal")
        composeTestRule.onNodeWithTag("cancelBtn").performClick()
        composeTestRule.onNodeWithTag("twitchAnnaCramling")
            .assertExists("No cargó el ícono de twitch").performClick()
        composeTestRule.onNodeWithTag("confirmBtn")
            .assertExists("No se dió click en el confirm button")
    }

    @Test
    fun clickOnYoutubeIcons() {
        composeTestRule.setContent {
            val controller = rememberNavController()
            // ← Simula insets
            NavHost(
                navController = controller,
                startDestination = ChessStreamersScreenNav,
                modifier = Modifier.windowInsetsPadding(insets = WindowInsets.statusBars)
            ) {
                composable<ChessStreamersScreenNav> {
                    SharedTransitionLayout {
                        AnimatedVisibility(
                            visible = true,
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                            Box(Modifier.windowInsetsPadding(WindowInsets.statusBars)) {
                                ChessStreamersScreen(
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
        }
        composeTestRule.onNodeWithTag("ChessStreamersScreen")
            .assertExists("No cargó la pantalla de streamers")
        composeTestRule.onNodeWithTag("streamersList")
        composeTestRule.onNodeWithTag("youtubeGothamchess")
            .assertExists("No cargó el ícono de twitch").performClick()
        composeTestRule.onNodeWithTag("AlertDialogChannel")
            .assertExists("No cargó el alert dialog para visitar el canal")
        composeTestRule.onNodeWithTag("cancelBtn").performClick()
        composeTestRule.onNodeWithTag("youtubeGothamchess")
            .assertExists("No cargó el ícono de twitch").performClick()
        composeTestRule.onNodeWithTag("confirmBtn")
            .assertExists("No se dió click en el confirm button")
    }
}