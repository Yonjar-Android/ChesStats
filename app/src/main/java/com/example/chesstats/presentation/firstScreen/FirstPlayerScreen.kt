package com.example.chesstats.presentation.firstScreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.chesstats.R
import com.example.chesstats.domain.models.ModeStats
import com.example.chesstats.domain.models.PlayerDomainModel
import com.example.chesstats.presentation.extras.ChargeScreen
import com.example.chesstats.presentation.extras.EditSpacer
import com.example.chesstats.presentation.extras.PlayerStreamPlatforms
import com.example.chesstats.presentation.extras.ZoomProfileScreen

@Composable
fun FirstPlayerScreen(
    hiltViewModel: FirstScreenViewModel
) {

    val player by hiltViewModel.player.collectAsStateWithLifecycle()

    val loading by hiltViewModel.loading.collectAsStateWithLifecycle()

    val error by hiltViewModel.error.collectAsStateWithLifecycle()

    val context = LocalContext.current

    if (error.isNotEmpty()) {
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            hiltViewModel.cleanError() }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0XFF101B23))
            .verticalScroll(state = rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var searchQuery by remember { mutableStateOf("") }

        EditSpacer()

        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxWidth(fraction = 0.95f)
                .clip(RoundedCornerShape(10.dp))
        ) {

            Spacer(modifier = Modifier.weight(0.1f))

            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0XFF223849))
                    .testTag("searchTF")
                    // Ajusta el peso para centrar el TextField
                    .clip(RoundedCornerShape(20.dp))
                    .height(60.dp)
                    .testTag("tfSearch"),
                placeholder = { Text(stringResource(R.string.search_str), color = Color.White) },
                singleLine = true,
                colors = androidx.compose.material3.TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                ),
                trailingIcon = {
                    IconButton(
                        modifier = Modifier.testTag("searchButton")
                            .padding(horizontal = 5.dp)
                            .clip(CircleShape),
                        onClick = {
                            hiltViewModel.searchPlayer(searchQuery)
                        }) {
                        Icon(
                            modifier = Modifier
                                .size(30.dp),
                            imageVector = Icons.Filled.Search,
                            contentDescription = "search icon",
                            tint = Color.White
                        )
                    }
                }
            )
        }

        EditSpacer(15.dp)

        PlayerProfileInfo(player)

        PlayerStatsInfo(player)

        EditSpacer(15.dp)

        PlayerStreamPlatforms(player)

    }

    // Loading
    if (loading) {
        ChargeScreen()
    }

}

@Composable
fun PlayerProfileInfo(player: PlayerDomainModel?) {
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
                .size(160.dp)
                .clip(CircleShape)
                .border(width = 4.dp, color = Color.White, shape = CircleShape)
                .clickable {
                    showZoom = true
                }
                .testTag("profileImage")
        )

        if (showZoom) {
            ZoomProfileScreen(image = player?.profileImage) { showZoom = false }
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
                color = Color.White
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
            text = "${stringResource(R.string.username_str)} ${player?.username}",
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


@Composable
fun PlayerStatsInfo(player: PlayerDomainModel?) {

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {

        EditSpacer(15.dp)

        Text(
            "Stats",
            color = Color.White, fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }

    EditSpacer()

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ChessModeItem(
            image = R.drawable.blitz,
            chessMode = stringResource(R.string.blitz_str),
            chessStats = player?.eloStats?.blitzStats
        )

        ChessModeItem(
            image = R.drawable.rapid,
            chessMode = stringResource(R.string.rapid_str),
            chessStats = player?.eloStats?.rapidStats
        )

        ChessModeItem(
            image = R.drawable.bullet,
            chessMode = stringResource(R.string.bullet_str),
            chessStats = player?.eloStats?.bulletStats
        )

        Row(
            modifier = Modifier.fillMaxWidth(fraction = 0.95f),
            verticalAlignment = Alignment.CenterVertically
        ) {

            if (player?.title != null) {

                Image(
                    painter = painterResource(R.drawable.title),
                    contentDescription = "icon",
                    modifier = Modifier.size(45.dp)
                )

                EditSpacer()

                Text(
                    text = "${stringResource(player.title.titled)} (${player.title.name})",
                    fontSize = 16.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun ChessModeItem(image: Int, chessMode: String, chessStats: ModeStats?) {
    Row(
        modifier = Modifier.fillMaxWidth(fraction = 0.95f),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = "icon"
        )

        EditSpacer()

        Column {
            TextRating(chessMode, chessStats)
        }
    }

    EditSpacer()
}


@Composable
fun TextRating(chessMode: String, stats: ModeStats?) {

    Column {

        Text(
            text = "${stats?.last} ( Best: ${stats?.best} )",
            fontSize = 16.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold
        )

        EditSpacer(2.dp)

        Text(
            text = "$chessMode ( ${stringResource(R.string.wins_str)} ${stats?.wins}, ${
                stringResource(
                    R.string.draws_str
                )
            } ${stats?.draws}, ${stringResource(R.string.losses_str)} ${stats?.losses} )",
            fontSize = 14.sp,
            color = Color(0XFF8FB0CC),
            textAlign = TextAlign.Center
        )

    }

}



