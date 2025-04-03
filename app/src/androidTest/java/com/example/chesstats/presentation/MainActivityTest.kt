package com.example.chesstats.presentation

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test

class MainActivityTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun clickFirstIconNavigation() {
        // Realiza el click
        composeTestRule.onNodeWithTag("firstIconNavigation").performClick()
        composeTestRule.onNodeWithTag("secondIconNavigation").performClick()
        composeTestRule.onNodeWithTag("thirdIconNavigation").performClick()
    }
}