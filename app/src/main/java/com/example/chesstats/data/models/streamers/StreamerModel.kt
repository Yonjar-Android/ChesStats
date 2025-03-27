package com.example.chesstats.data.models.streamers

import com.google.gson.annotations.SerializedName

data class StreamerObjectAPI(
    @SerializedName("streamers") val streamers: List<StreamerModel>
)

data class StreamerModel(
    @SerializedName("username") val username: String?,
    @SerializedName("avatar") val avatar: String?,
    @SerializedName("url") val urlUser: String?,
    @SerializedName("is_live") val isLive: Boolean?,
    @SerializedName("platforms") val platforms: List<StreamPlatform>?
)

data class StreamPlatform(
    @SerializedName("type") val platformName: String?,
    @SerializedName("channel_url") val channelUrl: String?,
    @SerializedName("is_live") val isLive: Boolean?
)
