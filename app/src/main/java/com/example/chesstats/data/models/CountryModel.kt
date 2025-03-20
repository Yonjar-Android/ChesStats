package com.example.chesstats.data.models

import com.google.gson.annotations.SerializedName

data class CountryModel(
    @SerializedName("@id") val id : String,
    @SerializedName("code") val code : String,
    @SerializedName("name") val name : String,
)