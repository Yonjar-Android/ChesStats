package com.example.chesstats.data.mappers

import com.example.chesstats.data.models.ChessStats
import com.example.chesstats.data.models.ProfileDataModel
import com.example.chesstats.domain.models.PlayerDomainModel
import com.example.chesstats.domain.models.PlayerEloStats

object PlayerMapper {
    fun playerDataModelToDomainModel(
        dataModel: ProfileDataModel?,
        dataStats: ChessStats?
    ): PlayerDomainModel{
        return PlayerDomainModel(
            playerId = dataModel?.playerId ?: 0L,
            id = dataModel?.id ?: "",
            name = dataModel?.name ?: "",
            username = dataModel?.username ?: "",
            country = dataModel?.country ?: "",
            profileImage = dataModel?.avatar ?: "",
            eloStats = PlayerEloStats(
                bestBlitz = dataStats?.chessBlitz?.best?.rating ?: 0,
                lastBlitz = dataStats?.chessBlitz?.last?.rating ?: 0,
                bestRapid = dataStats?.chessRapid?.best?.rating ?: 0,
                lastRapid = dataStats?.chessRapid?.best?.rating ?: 0,
                bestBullet = dataStats?.chessRapid?.best?.rating ?: 0,
                lastBullet = dataStats?.chessRapid?.best?.rating ?: 0,
                fide = dataStats?.fide ?: 0
            ),
            title = dataModel?.title
        )
    }
}