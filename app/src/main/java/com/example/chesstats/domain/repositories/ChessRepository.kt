package com.example.chesstats.domain.repositories

import com.example.chesstats.domain.models.PlayerDomainModel

interface ChessRepository {
    suspend fun getPlayerInfo(playerName: String): PlayerDomainModel?
}