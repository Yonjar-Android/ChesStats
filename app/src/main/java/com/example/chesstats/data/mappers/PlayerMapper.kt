package com.example.chesstats.data.mappers

import com.example.chesstats.data.models.ProfileDataModel
import com.example.chesstats.domain.models.PlayerDomainModel

object PlayerMapper {
    fun playerDataModelToDomainModel(
        dataModel: ProfileDataModel?
    ): PlayerDomainModel{
        return PlayerDomainModel(
            playerId = dataModel?.playerId ?: 0L,
            id = dataModel?.id ?: "",
            name = dataModel?.name ?: "",
            username = dataModel?.username ?: "",
            country = dataModel?.country ?: "",
            profileImage = dataModel?.avatar ?: ""
        )
    }
}