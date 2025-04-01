package com.example.chesstats

import com.example.chesstats.data.models.ChessBest
import com.example.chesstats.data.models.ChessLast
import com.example.chesstats.data.models.ChessMode
import com.example.chesstats.data.models.ChessRecord
import com.example.chesstats.data.models.ChessStats
import com.example.chesstats.data.models.CountryModel
import com.example.chesstats.data.models.LeaderBoardModel
import com.example.chesstats.data.models.ProfileDataModel
import com.example.chesstats.data.models.streamers.StreamPlatform
import com.example.chesstats.data.models.streamers.StreamerModel
import com.example.chesstats.data.models.streamers.StreamerObjectAPI

object MotherObject {

    val profileDataTest = ProfileDataModel(
        avatar = "Image",
        name = "Yonjar",
        username = "Yonjar132",
        rank = 1,
        title = "CM",
        playerId = 1,
        score = 1800,
        verified = false,
        followers = 10,
        streamingPlatforms = listOf(),
        isStreamer = false,
        id = "1",
        country = "https://api.chess.com/pub/country/NI",
        url = "UrlYonjar",
        league = null,
        lastOnline = null,
        joined = null,
        status = null
    )

    private val chessRecord = ChessRecord(
        win = 100,
        loss = 50,
        draw = 20
    )

    private val chessBest = ChessBest(
        rating = 1900 ,
        date = 0,
        game = ""
    )

    private val chessLast = ChessLast(
        rating = 1800,
        date = 0
    )

    private val rapidMode = ChessMode(
        best = chessBest,
        last = chessLast,
        record = chessRecord
    )

    private val blitzMode = ChessMode(
        best = chessBest.copy(rating = 1800),
        last = chessLast.copy(rating = 1700),
        record= chessRecord
    )

    private val bulletMode = ChessMode(
        best = chessBest.copy(rating = 1600),
        last = chessLast.copy(rating = 1500),
        record = chessRecord
    )

    val profileDataStatsTest = ChessStats(
        chessBlitz = blitzMode,
        chessRapid = rapidMode,
        chessBullet = bulletMode,
        fide = 0
    )

    val profileDataLeaderBoard1 = ProfileDataModel(
        avatar = "Image",
        name = "Magnus",
        username = "MagnusCarlsen",
        rank = 1,
        title = "GM",
        playerId = 1,
        score = 2800,
        verified = false,
        followers = 10000,
        streamingPlatforms = listOf(),
        isStreamer = false,
        id = "1",
        country = "Noruega",
        url = "UrlMagnus",
        league = null,
        lastOnline = null,
        joined = null,
        status = null
    )

    val profileDataLeaderBoard2 = ProfileDataModel(
        avatar = "Image",
        name = "Hikaru",
        username = "Nakamura",
        rank = 2,
        title = "GM",
        playerId = 2,
        score = 2700,
        verified = false,
        followers = 10000,
        streamingPlatforms = listOf(),
        isStreamer = false,
        id = "2",
        country = "USA",
        url = "UrlHikaru",
        league = null,
        lastOnline = null,
        joined = null,
        status = null
    )

    val leaderboard = LeaderBoardModel(
        blitz = listOf(profileDataLeaderBoard1,profileDataLeaderBoard2),
        bullet = listOf(profileDataLeaderBoard1,profileDataLeaderBoard2),
        rapid = listOf(profileDataLeaderBoard1,profileDataLeaderBoard2),

    )

    val streamerOne = StreamerModel(
        username = "AnnaCramling",
        avatar = "AnnaImage",
        platforms = listOf(
            StreamPlatform(
                platformName = "Twitch",
                channelUrl = "TwitchAnna",
                isLive = true
            )
        ),
        urlUser = "AnnaUrl",
        isLive = true
    )

    val streamerTwo = StreamerModel(
        username = "Gothamchess",
        avatar = "GothamImage",
        platforms = listOf(
            StreamPlatform(
                platformName = "Twitch",
                channelUrl = "TwitchGotham",
                isLive = true
            )
        ),
        urlUser = "GothamUrl",
        isLive = true
    )

    val streamersList = StreamerObjectAPI(
        streamers = listOf(streamerOne,streamerTwo)
    )

    val country = CountryModel(
        id = "1",
        code = "NI",
        name = "Nicaragua")


}