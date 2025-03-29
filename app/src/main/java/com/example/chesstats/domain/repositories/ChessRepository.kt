package com.example.chesstats.domain.repositories

import com.example.chesstats.data.models.CountryModel
import com.example.chesstats.data.models.FlagModel
import com.example.chesstats.data.models.LeaderBoardModel
import com.example.chesstats.data.models.streamers.StreamerModel
import com.example.chesstats.domain.models.PlayerDomainModel

interface ChessRepository {
    suspend fun getPlayerInfo(playerName: String): PlayerDomainModel?

    suspend fun getLeaderBoards(): LeaderBoardModel?

    suspend fun getStreamers(): List<StreamerModel>?

    suspend fun getCountryFromPlayer(endpoint: String): CountryModel?

    suspend fun getFlag(countryName: String): FlagModel?
}