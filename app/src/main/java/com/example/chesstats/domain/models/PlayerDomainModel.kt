package com.example.chesstats.domain.models

data class PlayerDomainModel(
    val profileImage: String,
    val playerId: Long,
    val id: String,
    val name: String,
    val username: String,
    val country: String,
    val eloStats: PlayerEloStats? = null,
    val title: String? = null
)