package com.example.chesstats.presentation.extras

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chesstats.R
import com.example.chesstats.domain.models.PlayerDomainModel
import com.example.chesstats.presentation.chessStreamersScreen.OpenLinkDialog

@Composable
fun PlayerStreamPlatforms(player: PlayerDomainModel?) {

    var showDialog by remember { mutableStateOf(false) }

    var selectedUrl by remember { mutableStateOf("") }

    val uriHandler = LocalUriHandler.current

    Column {

        if (player?.platforms?.isNotEmpty() == true) {

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

                EditSpacer(15.dp)

                Text(
                    "Stream Platforms",
                    color = MaterialTheme.colorScheme.onSurface, fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            for (i in player.platforms) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (i.platform == "twitch") {
                        IconButton(
                            onClick = {
                                selectedUrl = i.link ?: ""
                                showDialog = true
                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.twitch),
                                contentDescription = "Twitch icon",
                                tint = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.size(23.dp)
                            )
                        }

                    } else if (i.platform == "youtube") {
                        IconButton(
                            onClick = {
                                selectedUrl = i.link ?: ""
                                showDialog = true
                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.youtube),
                                contentDescription = "Youtube icon",
                                tint = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.height(30.dp)
                            )
                        }
                    }

                    EditSpacer(5.dp)

                    Text(i.platform?.replaceFirstChar { it.uppercase() } ?: "",
                        color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.padding(vertical = 10.dp))


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