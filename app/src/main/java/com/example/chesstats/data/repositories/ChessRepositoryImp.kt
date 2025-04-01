package com.example.chesstats.data.repositories

import com.example.chesstats.data.mappers.PlayerMapper
import com.example.chesstats.data.models.CountryModel
import com.example.chesstats.data.models.FlagModel
import com.example.chesstats.data.models.LeaderBoardModel
import com.example.chesstats.data.models.streamers.StreamerModel
import com.example.chesstats.data.network.services.PlayerService
import com.example.chesstats.domain.models.PlayerDomainModel
import com.example.chesstats.domain.repositories.ChessRepository
import com.example.chesstats.utils.ResultCase
import javax.inject.Inject

class ChessRepositoryImp @Inject constructor(
    private val playerService: PlayerService
) : ChessRepository {
    override suspend fun getPlayerInfo(playerName: String): ResultCase<PlayerDomainModel> {
        return try {
            val player = playerService.getPlayerInfo(playerName)
            val playerStats = playerService.getPlayerStatsInfo(playerName)

            if (player.body() == null || playerStats.body() == null) {
                ResultCase.Error(message = "Error: No se encontró al jugador")
            } else {
                ResultCase.Success(
                    PlayerMapper.playerDataModelToDomainModel(
                        dataModel = player.body(),
                        dataStats = playerStats.body()
                    )
                )
            }
        } catch (e: Exception) {
            ResultCase.Error("Error: ${e.message}")
        }
    }

    override suspend fun getLeaderBoards(): ResultCase<LeaderBoardModel> {
        return try {
            val leaderBoard = playerService.getLeaderBoards().body()
            if (leaderBoard == null) {
                ResultCase.Error("Error: No fue posible cargar los datos")
            } else {
                ResultCase.Success(
                    leaderBoard
                )
            }

        } catch (e: Exception) {
            ResultCase.Error("Error: ${e.message}")
        }
    }

    override suspend fun getStreamers(): ResultCase<List<StreamerModel>> {
        return try {
            val streamers = playerService.getStreamers().body()?.streamers
            if (streamers == null) {
                ResultCase.Error("Error: No fue posible cargar los datos")
            } else {
                ResultCase.Success(streamers.filter { it.isLive == true })
            }
        } catch (e: Exception) {
            ResultCase.Error("Error: ${e.message}")
        }
    }

    override suspend fun getCountryFromPlayer(endpoint: String): ResultCase<CountryModel> {
        return try {
            if (endpoint.isEmpty()) {
                ResultCase.Error("Error: No fue posible cargar el pais")
            } else{
                val countryCode =
                    playerService.getCountry(endpoint.substringAfter("https://api.chess.com/pub/country/"))
                        .body()

                if (countryCode == null) {
                    ResultCase.Error("Error: No fue posible cargar el pais")
                } else {
                    ResultCase.Success(countryCode)
                }
            }
        } catch (e: Exception) {
            ResultCase.Error("Error: ${e.message}")
        }
    }
}