package com.example.chesstats.data.network.services

import com.example.chesstats.data.models.ProfileDataModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PlayerService {

    @GET("pub/player/{username}")
    suspend fun getPlayerInfo(@Path("username") username: String): Response<ProfileDataModel>


}