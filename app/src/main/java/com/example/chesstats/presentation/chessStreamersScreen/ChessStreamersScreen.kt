@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.example.chesstats.presentation.chessStreamersScreen

import android.widget.Toast
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.chesstats.R
import com.example.chesstats.data.models.streamers.StreamerModel
import com.example.chesstats.presentation.extras.ChargeScreen
import com.example.chesstats.presentation.extras.DetailPlayerScreenNav
import com.example.chesstats.presentation.extras.EditSpacer

@Composable
fun SharedTransitionScope.ChessStreamersScreen(
    chessStreamersViewModel:
    ChessStreamersViewModel,
    controller: NavController,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    val streamers by chessStreamersViewModel.streamers.collectAsStateWithLifecycle()

    val loading by chessStreamersViewModel.loading.collectAsStateWithLifecycle()

    val error by chessStreamersViewModel.error.collectAsStateWithLifecycle()

    val context = LocalContext.current

    LaunchedEffect(error) {
        if (error.isNotEmpty()){
            Toast.makeText(context,error, Toast.LENGTH_SHORT).show()
            chessStreamersViewModel.cleanError()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0XFF101B23)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EditSpacer()

        Text("Streamers", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold)

        EditSpacer(20.dp)

        LazyColumn {
            items(streamers) { streamer ->
                SteamerItem(streamer, controller, animatedVisibilityScope)

                EditSpacer()
            }
        }
    }

    if (loading) ChargeScreen()

}

@Composable
fun SharedTransitionScope.SteamerItem(
    streamer: StreamerModel?,
    controller: NavController,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedUrl by remember { mutableStateOf("") }

    val uriHandler = LocalUriHandler.current

    Row(
        modifier = Modifier
            .fillMaxWidth(fraction = 0.95f)
            .clickable {
                controller.navigate(DetailPlayerScreenNav(streamer?.username!!))
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = streamer?.avatar,
                contentDescription = "profile picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .sharedElement(
                        state = rememberSharedContentState(key = "image/${streamer?.username?.lowercase()}"),
                        animatedVisibilityScope = animatedVisibilityScope,
                        boundsTransform = { _, _ -> tween(durationMillis = 250) }
                    )
                    .size(65.dp)
                    .clip(CircleShape)
                    .border(width = 1.dp, color = Color.White, shape = CircleShape)
            )

            EditSpacer()

            Column {

                Text(
                    text = streamer?.username ?: "",
                    fontSize = 16.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.sharedElement(
                        state = rememberSharedContentState(key = "username/${streamer?.username?.lowercase()}"),
                        animatedVisibilityScope = animatedVisibilityScope,
                        boundsTransform = { _, _ -> tween(durationMillis = 250) }
                    )
                )

                EditSpacer(2.dp)

                if (streamer?.platforms?.isNotEmpty() == true) {
                    for (i in streamer.platforms) {
                        Row {
                            Text(i.platformName?.replaceFirstChar { it.uppercase() } ?: "",
                                color = Color.White)

                            EditSpacer(5.dp)

                            if (i.isLive == true) {
                                Text("Live", color = Color.Green)
                            }
                        }

                    }
                }
            }
        }

        if (streamer?.platforms?.isNotEmpty() == true) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                for (i in streamer.platforms) {
                    if (i.platformName == "twitch") {
                        IconButton(
                            onClick = {
                                selectedUrl = i.channelUrl ?: ""
                                showDialog = true
                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.twitch),
                                contentDescription = "Twitch icon",
                                tint = Color.White,
                                modifier = Modifier.size(23.dp)
                            )
                        }

                    } else if (i.platformName == "youtube") {
                        IconButton(
                            onClick = {
                                selectedUrl = i.channelUrl ?: ""
                                showDialog = true
                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.youtube),
                                contentDescription = "Youtube icon",
                                tint = Color.White,
                                modifier = Modifier.height(30.dp)
                            )
                        }

                    }
                }
            }
        }
    }

    if (showDialog) {
        OpenLinkDialog(
            selectedUrl, uriHandler,
            closeDialog = {
                showDialog = false
            })
    }
}

@Composable
fun OpenLinkDialog(url: String, uriHandler: UriHandler, closeDialog: () -> Unit) {
    AlertDialog(
        onDismissRequest = { },
        title = { Text("¿Abrir enlace externo?") },
        text = { Text("Serás redirigido a $url") },
        confirmButton = {
            Button(
                onClick = {
                    closeDialog()
                    uriHandler.openUri(url)
                }
            ) {
                Text("Abrir")
            }
        },
        dismissButton = {
            TextButton(
                onClick = { closeDialog() }
            ) {
                Text("Cancelar")
            }
        }
    )
}