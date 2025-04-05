package com.example.chesstats

import com.example.chesstats.data.models.CountryModel
import com.example.chesstats.data.models.LeaderBoardModel
import com.example.chesstats.data.models.ProfileDataModel
import com.example.chesstats.data.models.streamers.StreamPlatform
import com.example.chesstats.data.models.streamers.StreamerModel
import com.example.chesstats.data.models.streamers.StreamerObjectAPI
import com.example.chesstats.domain.models.PlayerDomainModel
import com.example.chesstats.utils.ResultCase

object MotherObjectUI {
    val resultPlayerDomain = ResultCase.Success(
        PlayerDomainModel(
            playerId = 10,
            id = "10",
            profileImage = "YonjarImage",
            name = "Juan Centeno",
            username = "Yonjar",
            country = "UrlNicaragua",
            countryName = "Nicaragua",
            platforms = listOf()
        )
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

    val leaderboard = ResultCase.Success(LeaderBoardModel(
        blitz = listOf(profileDataLeaderBoard1,profileDataLeaderBoard2),
        bullet = listOf(profileDataLeaderBoard1,profileDataLeaderBoard2),
        rapid = listOf(profileDataLeaderBoard1,profileDataLeaderBoard2),
        ))

    val streamerOne = StreamerModel(
        username = "AnnaCramling",
        avatar = "AnnaImage",
        platforms = listOf(
            StreamPlatform(
                platformName = "twitch",
                channelUrl = "TwitchAnnaCramling",
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
                platformName = "youtube",
                channelUrl = "YoutubeGothamChess",
                isLive = true
            )
        ),
        urlUser = "GothamUrl",
        isLive = true
    )

    val streamersList = ResultCase.Success(listOf(streamerOne,streamerTwo)
    )

    val country = ResultCase.Success(CountryModel(
        id = "1",
        code = "NI",
        name = "Nicaragua"))
}