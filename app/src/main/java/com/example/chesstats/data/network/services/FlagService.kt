package com.example.chesstats.data.network.services

import com.example.chesstats.data.models.FlagModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface FlagService {
    @GET("name/{name}?fullText=true")
    suspend fun getCountry(@Path("name") countryName: String): Response<List<FlagModel>>
}