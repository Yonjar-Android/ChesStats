@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.example.chesstats.presentation.detailPlayerScreen

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.chesstats.R
import com.example.chesstats.domain.models.PlayerDomainModel
import com.example.chesstats.presentation.extras.ChargeScreen
import com.example.chesstats.presentation.extras.EditSpacer
import com.example.chesstats.presentation.extras.PlayerStreamPlatforms
import com.example.chesstats.presentation.extras.ZoomProfileScreen
import com.example.chesstats.presentation.firstScreen.PlayerStatsInfo

@Composable
fun SharedTransitionScope.DetailPlayerScreen(
    username: String,
    detailPlayerViewModel: DetailPlayerViewModel,
    controller: NavController,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    val player by detailPlayerViewModel.player.collectAsStateWithLifecycle()

    val loading by detailPlayerViewModel.loading.collectAsStateWithLifecycle()

    val error by detailPlayerViewModel.error.collectAsStateWithLifecycle()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        detailPlayerViewModel.searchPlayer(username)
    }

    LaunchedEffect(error) {
        if (error.isNotEmpty()) Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        detailPlayerViewModel.cleanError()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0XFF101B23))
            .verticalScroll(state = rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            IconButton(
                onClick = { controller.navigateUp() }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "arrow Back",
                    tint = Color.White, modifier = Modifier.size(30.dp)
                )
            }
        }

        EditSpacer(15.dp)

        PlayerDetailProfileInfo(
            player, animatedVisibilityScope = animatedVisibilityScope
        )

        PlayerStatsInfo(player)

        EditSpacer(15.dp)

        PlayerStreamPlatforms(player)

        BackHandler {
            controller.navigateUp()
        }
    }

    if (loading) ChargeScreen()
}

@Composable
fun SharedTransitionScope.PlayerDetailProfileInfo(
    player: PlayerDomainModel?,
    animatedVisibilityScope: AnimatedVisibilityScope
) {

    Column(
        modifier = Modifier
            .fillMaxWidth(fraction = 0.9f)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.Transparent)
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var showZoom by remember { mutableStateOf(false) }

        AsyncImage(
            model = player?.profileImage,
            contentDescription = "Profile Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .sharedElement(
                    state = rememberSharedContentState(key = "image/${player?.username?.lowercase()}"),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = { _, _ -> tween(durationMillis = 250) }
                )
                .size(150.dp)
                .clip(CircleShape)
                .border(width = 4.dp, color = Color.White, shape = CircleShape)
                .clickable { showZoom = true }
        )

        if (showZoom) {
            ZoomProfileScreen(player?.profileImage) { showZoom = false }
        }

        EditSpacer()

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                player?.name ?: "",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier.sharedElement(
                    state = rememberSharedContentState(key = "username/${player?.username?.lowercase()}"),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = { _, _ -> tween(durationMillis = 250) }
                )
            )

            if (player?.title != null) {
                EditSpacer()

                Text(
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color(0XFFA62934))
                        .padding(vertical = 2.dp, horizontal = 5.dp),
                    text = player.title.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.White,
                )
            }
        }

        Text(
            text = "Username: ${player?.username}",
            fontSize = 14.sp,
            color = Color(0XFF8FB0CC),
            textAlign = TextAlign.Center
        )

        if (player?.countryName?.isNotEmpty() == true){
            Text(
                text = "${stringResource(R.string.country_str)} ${player.countryName}",
                fontSize = 14.sp,
                color = Color(0XFF8FB0CC),
                textAlign = TextAlign.Center
            )
        }
    }
}