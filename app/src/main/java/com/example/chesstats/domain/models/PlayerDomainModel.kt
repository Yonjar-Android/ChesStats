package com.example.chesstats.domain.models

import com.example.chesstats.data.models.StreamPlatformModel
import com.example.chesstats.presentation.firstScreen.TitledMasters

data class PlayerDomainModel(
    val profileImage: String,
    val playerId: Long,
    val id: String,
    val name: String,
    val username: String,
    val country: String,
    val eloStats: PlayerEloStats? = null,
    val title: TitledMasters? = null,
    val platforms: List<StreamPlatformModel>?,
    val countryName:String = ""
)