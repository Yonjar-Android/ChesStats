package com.example.chesstats.presentation.leaderboardScreen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.chesstats.data.models.ProfileDataModel
import com.example.chesstats.presentation.extras.ChargeScreen
import com.example.chesstats.presentation.firstScreen.EditSpacer


@Composable
fun LeaderBoardScreen(
    leaderBoardViewModel: LeaderBoardViewModel,
    controller: NavController
) {

    val rapidPlayers by leaderBoardViewModel.rapidPlayers.collectAsState()
    val blitzPlayers by leaderBoardViewModel.blitzPlayers.collectAsState()
    val bulletPlayers by leaderBoardViewModel.bulletPlayers.collectAsState()

    val loading by leaderBoardViewModel.loading.collectAsState()

    val tabs = listOf<String>("Blitz", "Rapid", "Bullet")

    val selectedTab = remember { mutableIntStateOf(0) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0XFF101B23))
    ) {


        TabRow(
            modifier = Modifier
                .padding(10.dp)
                .clip(RoundedCornerShape(10.dp)),
            selectedTabIndex = selectedTab.intValue,
            containerColor = Color(0XFF223849),
            divider = {},
            indicator = {},
        ) {
            tabs.forEachIndexed { index, title ->

                val isSelected = selectedTab.intValue == index

                val backgroundColor by animateColorAsState(
                    targetValue = if (isSelected) Color(0XFF101B23) else Color.Transparent,
                    animationSpec = tween(durationMillis = 200)
                )
                val textColor by animateColorAsState(
                    targetValue = if (isSelected) Color.White else Color.White.copy(alpha = 0.8f),
                    animationSpec = tween(durationMillis = 200)
                )

                if (isSelected) {
                    Tab(
                        modifier = Modifier
                            .height(30.dp)
                            .padding(6.dp) // Padding entre tabs
                            .clip(RoundedCornerShape(8.dp)) // Bordes redondeados
                            .background(backgroundColor)
                            .animateContentSize(),
                        selected = selectedTab.intValue == index,
                        onClick = { selectedTab.intValue = index },
                        text = {
                            Text(
                                text = title, color = textColor,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    )
                } else {
                    Tab(
                        selected = selectedTab.intValue == index,
                        onClick = { selectedTab.intValue = index },
                        text = { Text(text = title, color = Color.White) }
                    )
                }

            }
        }

        EditSpacer()

        when (selectedTab.intValue) {
            0 -> {
                LeaderBoardModeScreen(rapidPlayers, controller)
            }

            1 -> {
                LeaderBoardModeScreen(blitzPlayers, controller)
            }

            2 -> {
                LeaderBoardModeScreen(bulletPlayers, controller)
            }
        }

        if (loading) {
            ChargeScreen()
        }
    }

    BackHandler {}
}

@Composable
fun LeaderBoardModeScreen(
    players: List<ProfileDataModel>?,
    controller: NavController
) {
    LazyColumn {
        players?.let {
            items(players) {
                PlayerRankItem(it, controller = controller)
                Spacer(modifier = Modifier.size(10.dp))
            }
        }
    }
}

@Composable
fun PlayerRankItem(player: ProfileDataModel, controller: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .clickable {
                controller.navigate("DetailPlayerScreen/${player.username}")
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = player.avatar,
                contentDescription = "profile picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(65.dp)
                    .clip(CircleShape)
            )

            EditSpacer()

            Column {

                Text(
                    text = player.name.takeUnless { it.isNullOrEmpty() } ?: player.username ?: "",
                    fontSize = 16.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold
                )

                EditSpacer(2.dp)

                Text(
                    text = "${player.score}",
                    fontSize = 14.sp,
                    color = Color(0XFF8FB0CC),
                    textAlign = TextAlign.Center
                )

            }
        }
        Text("#${player.rank}", fontSize = 16.sp, color = Color.White)

    }

}
