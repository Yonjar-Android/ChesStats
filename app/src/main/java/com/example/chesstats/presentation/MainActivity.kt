package com.example.chesstats.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chesstats.presentation.firstScreen.FirstPlayerScreen
import com.example.chesstats.presentation.firstScreen.FirstScreenViewModel
import com.example.chesstats.ui.theme.ChesStatsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val firstScreenViewModel: FirstScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChesStatsTheme {
                FirstPlayerScreen(firstScreenViewModel)
            }
        }
    }
}
