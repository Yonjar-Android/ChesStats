package com.example.chesstats.data.models

import com.google.gson.annotations.SerializedName

data class LeaderBoardModel(
    @SerializedName("live_blitz") val blitz: List<ProfileDataModel>,
    @SerializedName("live_rapid") val rapid: List<ProfileDataModel>,
    @SerializedName("live_bullet") val bullet: List<ProfileDataModel>
)
