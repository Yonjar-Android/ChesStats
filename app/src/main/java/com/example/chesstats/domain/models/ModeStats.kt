package com.example.chesstats.domain.models

data class ModeStats(
    val best: Int,
    val last: Int,
    val wins: Int,
    val losses: Int,
    val draws: Int
)
