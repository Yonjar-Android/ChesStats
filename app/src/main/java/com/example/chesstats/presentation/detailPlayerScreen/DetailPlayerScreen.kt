package com.example.chesstats.presentation.detailPlayerScreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chesstats.presentation.extras.ChargeScreen
import com.example.chesstats.presentation.firstScreen.EditSpacer
import com.example.chesstats.presentation.firstScreen.PlayerProfileInfo
import com.example.chesstats.presentation.firstScreen.PlayerStatsInfo

@Composable
fun DetailPlayerScreen(
    username: String,
    detailPlayerViewModel: DetailPlayerViewModel,
    controller: NavController
) {

    val player by detailPlayerViewModel.player.collectAsState()

    val loading by detailPlayerViewModel.loading.collectAsState()

    LaunchedEffect(Unit) {
        detailPlayerViewModel.searchPlayer(username)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0XFF101B23))
            .windowInsetsPadding(WindowInsets.systemBars),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.fillMaxWidth()){
            IconButton(
                onClick = {controller.navigateUp()}
            ) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "arrow Back",
                    tint = Color.White, modifier = Modifier.size(30.dp))
            }
        }


        EditSpacer(15.dp)

        PlayerProfileInfo(player)

        EditSpacer(sizeDp = 40.dp)

        PlayerStatsInfo(player)

        BackHandler {
            controller.navigateUp()
        }
    }

    if (loading) ChargeScreen()
}