package com.example.chesstats.data.models

import com.google.gson.annotations.SerializedName

data class StreamPlatformModel(
    @SerializedName("type") val platform: String?,
    @SerializedName("channel_url") val link: String?
)
