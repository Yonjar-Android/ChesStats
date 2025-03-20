package com.example.chesstats.data.repositories

import com.example.chesstats.data.mappers.PlayerMapper
import com.example.chesstats.data.network.services.PlayerService
import com.example.chesstats.domain.models.PlayerDomainModel
import com.example.chesstats.domain.repositories.ChessRepository
import javax.inject.Inject

class ChessRepositoryImp @Inject constructor(
    private val playerService: PlayerService
): ChessRepository {
    override suspend fun getPlayerInfo(playerName: String): PlayerDomainModel? {
        val player = playerService.getPlayerInfo(playerName)
        val finalData = PlayerMapper.playerDataModelToDomainModel(player.body())
        return finalData
    }
}