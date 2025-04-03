package com.example.chesstats.presentation.firstScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import com.example.chesstats.FakeRepository
import org.junit.Rule
import org.junit.Test

class FirstPlayerScreenKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    val viewModel = FirstScreenViewModel(FakeRepository())

    @Test
    fun screenChargeCorrectly() {
        composeTestRule.setContent {
                Box(Modifier.windowInsetsPadding(WindowInsets.statusBars)) {  // ← Simula insets
                    FirstPlayerScreen(viewModel)
            }
        }

        composeTestRule.onNodeWithTag("searchTF")
            .assertExists("No aparece el textfield de búsqueda")
        composeTestRule.onNodeWithTag("profileImage")
            .assertExists("Se muestra la foto de perfil del usuario")
    }

    @Test
    fun doSearchInTextField() {
        composeTestRule.setContent {
            Box(Modifier.windowInsetsPadding(WindowInsets.statusBars)) {  // ← Simula insets
                FirstPlayerScreen(viewModel)
            }
        }
        composeTestRule.onNodeWithTag("searchTF").performTextInput("Yonjar")
        composeTestRule.onNodeWithTag("searchButton").performClick()
    }

    @Test
    fun doSearchInTextFieldAndDeleteText() {
        composeTestRule.setContent {
            Box(Modifier.windowInsetsPadding(WindowInsets.statusBars)) {  // ← Simula insets
                FirstPlayerScreen(viewModel)
            }
        }
        composeTestRule.onNodeWithTag("searchTF").performTextInput("Yonjar")
        composeTestRule.onNodeWithTag("searchButton").performClick()
        composeTestRule.onNodeWithTag("searchTF").performTextClearance()
    }

    @Test
    fun doZoomOnProfilePicture() {
        composeTestRule.setContent {
            Box(Modifier.windowInsetsPadding(WindowInsets.statusBars)) {  // ← Simula insets
                FirstPlayerScreen(viewModel)
            }
        }
        composeTestRule.onNodeWithTag("profileImage").performClick()
        composeTestRule.onNodeWithTag("profileImageZoomed").assertExists("No se realizó el zoom")
        composeTestRule.onNodeWithTag("profileImageZoomed").performClick()
    }

}