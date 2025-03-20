package com.example.chesstats.domain.models

data class PlayerEloStats(
    val bestBlitz: Int,
    val lastBlitz: Int,
    val bestRapid: Int,
    val lastRapid: Int,
    val bestBullet: Int,
    val lastBullet: Int,
    val fide: Int = 0
)
