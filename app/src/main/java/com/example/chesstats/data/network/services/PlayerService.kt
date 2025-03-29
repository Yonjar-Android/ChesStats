package com.example.chesstats.data.network.services

import com.example.chesstats.data.models.ChessStats
import com.example.chesstats.data.models.CountryModel
import com.example.chesstats.data.models.LeaderBoardModel
import com.example.chesstats.data.models.ProfileDataModel
import com.example.chesstats.data.models.streamers.StreamerObjectAPI
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PlayerService {

    @GET("pub/player/{username}")
    suspend fun getPlayerInfo(@Path("username") username: String): Response<ProfileDataModel>

    @GET("pub/player/{username}/stats")
    suspend fun getPlayerStatsInfo(@Path("username") username: String): Response<ChessStats>

    @GET("pub/leaderboards")
    suspend fun getLeaderBoards(): Response<LeaderBoardModel>

    @GET("pub/streamers")
    suspend fun getStreamers(): Response<StreamerObjectAPI>

    @GET("pub/country/{code}")
    suspend fun getCountry(@Path("code") countryCode: String): Response<CountryModel>

}