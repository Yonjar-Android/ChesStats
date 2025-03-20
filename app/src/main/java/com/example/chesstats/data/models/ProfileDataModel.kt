package com.example.chesstats.data.models

import com.google.gson.annotations.SerializedName

data class ProfileDataModel(
 @SerializedName("avatar") val avatar: String,
 @SerializedName("player_id") val playerId: Long,
 @SerializedName("@id") val id: String,
 @SerializedName("url") val url: String,
 @SerializedName("name") val name: String,
 @SerializedName("username") val username: String,
 @SerializedName("followers") val followers: Int,
 @SerializedName("country") val country: String,
 @SerializedName("last_online") val lastOnline: Long,
 @SerializedName("joined") val joined: Long,
 @SerializedName("status") val status: String,
 @SerializedName("is_streamer") val isStreamer: Boolean,
 @SerializedName("verified") val verified: Boolean,
 @SerializedName("league") val league: String,
 @SerializedName("streaming_platforms") val streamingPlatforms: List<String>
)
