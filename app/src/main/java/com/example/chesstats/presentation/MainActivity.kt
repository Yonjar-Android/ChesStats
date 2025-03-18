package com.example.chesstats.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.chesstats.presentation.firstScreen.FirstPlayerScreen
import com.example.chesstats.ui.theme.ChesStatsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChesStatsTheme {
                FirstPlayerScreen()
            }
        }
    }
}
