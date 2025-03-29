package com.example.chesstats.data.repositories

import com.example.chesstats.data.mappers.PlayerMapper
import com.example.chesstats.data.models.CountryModel
import com.example.chesstats.data.models.FlagModel
import com.example.chesstats.data.models.LeaderBoardModel
import com.example.chesstats.data.models.streamers.StreamerModel
import com.example.chesstats.data.network.services.FlagService
import com.example.chesstats.data.network.services.PlayerService
import com.example.chesstats.domain.models.PlayerDomainModel
import com.example.chesstats.domain.repositories.ChessRepository
import javax.inject.Inject

class ChessRepositoryImp @Inject constructor(
    private val playerService: PlayerService,
        private val flagService: FlagService
) : ChessRepository {
    override suspend fun getPlayerInfo(playerName: String): PlayerDomainModel? {
        return try {
            val player = playerService.getPlayerInfo(playerName)
            val playerStats = playerService.getPlayerStatsInfo(playerName)
            if (player.body() == null || playerStats.body() == null) {
                null
            } else {
                PlayerMapper.playerDataModelToDomainModel(
                    dataModel = player.body()!!,
                    dataStats = playerStats.body()!!
                )
            }
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getLeaderBoards(): LeaderBoardModel? {
        return try {
            playerService.getLeaderBoards().body()
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getStreamers(): List<StreamerModel>? {
        return try {
            val streamers = playerService.getStreamers().body()?.streamers
            streamers?.filter { it.isLive == true }
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getCountryFromPlayer(endpoint: String): CountryModel? {
        return try {
            if (endpoint.isEmpty()) null
            playerService.getCountry(endpoint.substringAfter("https://api.chess.com/pub/country/")).body()
        } catch (e: Exception){
            null
        }
    }

    override suspend fun getFlag(countryName: String): FlagModel? {
        return try {

            if (countryName.isEmpty()) null
            flagService.getCountry(countryName).body()?.get(0)
        } catch (e: Exception){
            println(e.message)
            null
        }
    }
}