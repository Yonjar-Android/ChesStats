package com.example.chesstats.utils

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

object NavAnimations {
    fun enterAnimation(): EnterTransition {
        return slideInHorizontally(
            initialOffsetX = { fullWidth -> fullWidth },
            animationSpec = tween(durationMillis = 250)
        ) + fadeIn(animationSpec = tween(250))
    }

    fun exitAnimation(): ExitTransition {
        return slideOutHorizontally(
            targetOffsetX = { fullWidth -> -fullWidth },
            animationSpec = tween(durationMillis = 250)
        ) + fadeOut(animationSpec = tween(250))
    }

    fun popEnterAnimation(): EnterTransition {
        return slideInHorizontally(
            initialOffsetX = { fullWidth -> -fullWidth },
            animationSpec = tween(durationMillis = 250)
        ) + fadeIn(animationSpec = tween(250))
    }

    fun popExitAnimation(): ExitTransition {
        return slideOutHorizontally(
            targetOffsetX = { fullWidth -> fullWidth },
            animationSpec = tween(durationMillis = 250)
        ) + fadeOut(animationSpec = tween(250))
    }
}