package com.example.chesstats

import com.example.chesstats.data.models.CountryModel
import com.example.chesstats.data.models.LeaderBoardModel
import com.example.chesstats.data.models.streamers.StreamerModel
import com.example.chesstats.domain.models.PlayerDomainModel
import com.example.chesstats.domain.repositories.ChessRepository
import com.example.chesstats.utils.ResultCase

class FakeRepository(): ChessRepository {
    override suspend fun getPlayerInfo(playerName: String): ResultCase<PlayerDomainModel> {
        return MotherObjectUI.resultPlayerDomain
    }

    override suspend fun getLeaderBoards(): ResultCase<LeaderBoardModel?> {
        return MotherObjectUI.leaderboard
    }

    override suspend fun getStreamers(): ResultCase<List<StreamerModel>?> {
        return MotherObjectUI.streamersList
    }

    override suspend fun getCountryFromPlayer(endpoint: String): ResultCase<CountryModel?> {
        return MotherObjectUI.country
    }
}