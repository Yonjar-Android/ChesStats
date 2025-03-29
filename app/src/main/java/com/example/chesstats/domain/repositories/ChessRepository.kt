package com.example.chesstats.domain.repositories

import com.example.chesstats.data.models.CountryModel
import com.example.chesstats.data.models.FlagModel
import com.example.chesstats.data.models.LeaderBoardModel
import com.example.chesstats.data.models.streamers.StreamerModel
import com.example.chesstats.domain.models.PlayerDomainModel
import com.example.chesstats.utils.ResultCase

interface ChessRepository {
    suspend fun getPlayerInfo(playerName: String): ResultCase<PlayerDomainModel>

    suspend fun getLeaderBoards(): ResultCase<LeaderBoardModel?>

    suspend fun getStreamers(): ResultCase<List<StreamerModel>?>

    suspend fun getCountryFromPlayer(endpoint: String): ResultCase<CountryModel?>

    suspend fun getFlag(countryName: String): ResultCase<FlagModel?>
}