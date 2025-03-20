package com.example.chesstats.presentation.firstScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

@Composable
fun FirstPlayerScreen(
    hiltViewModel: FirstScreenViewModel
) {

    val player by hiltViewModel.player.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var searchQuery by remember { mutableStateOf("") }

        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Spacer(modifier = Modifier.weight(0.1f))

            // TextField (centrado)
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .weight(2f) // Ajusta el peso para centrar el TextField
                    .clip(RoundedCornerShape(20.dp))
                    .height(60.dp)
                    .testTag("tfSearch"),
                placeholder = { Text("Buscar", color = Color.White) },
                singleLine = true,
                colors = androidx.compose.material3.TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White,
                    cursorColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                )
            )

            IconButton(
                onClick = {

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

        EditSpacer()

        Column(
            modifier = Modifier
                .fillMaxWidth(fraction = 0.9f)
                .clip(RoundedCornerShape(20.dp))
                .background(Color.DarkGray)
                .padding(vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = player?.profileImage,
                contentDescription = "Profile Image",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .size(125.dp)
                    .clip(CircleShape)
            )

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



                if (player?.title != null){
                    EditSpacer()

                    Text(
                        modifier = Modifier
                            .clip(RoundedCornerShape(5.dp))
                            .background(Color(0XFFA62934))
                            .padding(vertical = 2.dp, horizontal = 5.dp),
                        text = player?.title ?: "",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.White,
                    )
                }
            }

            EditSpacer()

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column {
                    TextRating("Blitz")
                    TextRating(player?.eloStats?.lastBlitz.toString()) }

                Column {
                    TextRating("Rapid")
                    TextRating(player?.eloStats?.lastRapid.toString()) }

                Column {
                    TextRating("Bullet")
                    TextRating(player?.eloStats?.lastBullet.toString()) }

                Column {
                    TextRating("Fide")
                    TextRating(player?.eloStats?.fide.toString()) }
            }
        }

        EditSpacer(sizeDp = 40.dp)

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {

            EditSpacer(15.dp)

            Text(
                "Strongest Defeated Rivals",
                color = Color.White, fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        EditSpacer()

        Column(
            modifier = Modifier
                .fillMaxWidth(fraction = 0.9f)
                .clip(RoundedCornerShape(20.dp))
                .background(Color.DarkGray)
                .padding(12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            StrongestPlayerDefeatedItem(
                image = "https://upload.wikimedia.org/wikipedia/commons/thumb/5/56/FIDE_World_FR_Chess_Championship_2019_-_Magnus_Carlsen_%28cropped1%29.jpg/800px-FIDE_World_FR_Chess_Championship_2019_-_Magnus_Carlsen_%28cropped1%29.jpg",
                playerName = "Magnus Carlsen",
            )

            EditSpacer()

            StrongestPlayerDefeatedItem(
                image = "https://upload.wikimedia.org/wikipedia/commons/thumb/5/56/FIDE_World_FR_Chess_Championship_2019_-_Magnus_Carlsen_%28cropped1%29.jpg/800px-FIDE_World_FR_Chess_Championship_2019_-_Magnus_Carlsen_%28cropped1%29.jpg",
                playerName = "Magnus Carlsen",
            )

            EditSpacer()

            StrongestPlayerDefeatedItem(
                image = "https://upload.wikimedia.org/wikipedia/commons/thumb/5/56/FIDE_World_FR_Chess_Championship_2019_-_Magnus_Carlsen_%28cropped1%29.jpg/800px-FIDE_World_FR_Chess_Championship_2019_-_Magnus_Carlsen_%28cropped1%29.jpg",
                playerName = "Magnus Carlsen",
            )
        }
    }
}

@Composable
fun TextRating(text: String) {

    Text(
        text = text,
        fontSize = 14.sp,
        color = Color.White,
        textAlign = TextAlign.Center
    )
}

@Composable
fun StrongestPlayerDefeatedItem(
    image: String,
    playerName: String
){
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = image,
            contentDescription = "Profile Image",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.size(10.dp))

        Text(playerName, color = Color.White,
            fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
    }
}

@Composable
fun EditSpacer(
    sizeDp: Dp = 10.dp
){
    Spacer(modifier = Modifier.size(sizeDp))
}
