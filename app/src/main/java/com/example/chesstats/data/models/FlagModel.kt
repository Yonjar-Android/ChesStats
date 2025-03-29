package com.example.chesstats.data.models

import com.google.gson.annotations.SerializedName

data class FlagModel(
    @SerializedName("name") val country: CountryNameModel,
    @SerializedName("flags") val flag: FlagImageModel
)

data class CountryNameModel(
    @SerializedName("common") val name:String
)

data class FlagImageModel(
    @SerializedName("png") val flagImage:String
)
