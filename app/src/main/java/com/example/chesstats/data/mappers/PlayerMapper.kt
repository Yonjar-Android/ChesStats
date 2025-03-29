package com.example.chesstats.data.mappers

import com.example.chesstats.data.models.ChessStats
import com.example.chesstats.data.models.ProfileDataModel
import com.example.chesstats.domain.models.ModeStats
import com.example.chesstats.domain.models.PlayerDomainModel
import com.example.chesstats.domain.models.PlayerEloStats
import com.example.chesstats.presentation.firstScreen.TitledMasters

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
            profileImage = dataModel?.avatar ?: "https://airtonalonso.com/wp-content/uploads/2024/07/Chesscom.jpg",
            eloStats = PlayerEloStats(
                bestBlitz = dataStats?.chessBlitz?.best?.rating ?: 0,
                lastBlitz = dataStats?.chessBlitz?.last?.rating ?: 0,
                bestRapid = dataStats?.chessRapid?.best?.rating ?: 0,
                lastRapid = dataStats?.chessRapid?.best?.rating ?: 0,
                bestBullet = dataStats?.chessRapid?.best?.rating ?: 0,
                lastBullet = dataStats?.chessRapid?.best?.rating ?: 0,
                fide = dataStats?.fide ?: 0,
                rapidStats = ModeStats(
                    wins = dataStats?.chessRapid?.record?.win ?: 0,
                    losses = dataStats?.chessRapid?.record?.loss ?: 0,
                    draws = dataStats?.chessRapid?.record?.draw ?: 0,
                    best = dataStats?.chessRapid?.best?.rating ?: 0,
                    last = dataStats?.chessRapid?.last?.rating ?: 0,
                ),
                blitzStats = ModeStats(
                    wins = dataStats?.chessBlitz?.record?.win ?: 0,
                    losses = dataStats?.chessBlitz?.record?.loss ?: 0,
                    draws = dataStats?.chessBlitz?.record?.draw ?: 0,
                    best = dataStats?.chessBlitz?.best?.rating ?: 0,
                    last = dataStats?.chessBlitz?.last?.rating ?: 0,
                ),
                bulletStats = ModeStats(
                    wins = dataStats?.chessBullet?.record?.win ?: 0,
                    losses = dataStats?.chessBullet?.record?.loss ?: 0,
                    draws = dataStats?.chessBullet?.record?.draw ?: 0,
                    best = dataStats?.chessBullet?.best?.rating ?: 0,
                    last = dataStats?.chessBullet?.last?.rating ?: 0,
                )
            ),
            title = TitledMasters.fromString(dataModel?.title),
            platforms = dataModel?.streamingPlatforms
        )
    }
}