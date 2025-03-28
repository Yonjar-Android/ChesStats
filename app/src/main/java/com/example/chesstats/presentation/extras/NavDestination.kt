package com.example.chesstats.presentation.extras

import kotlinx.serialization.Serializable

@Serializable
object FirstPlayerScreenNav

@Serializable
object LeaderBoardScreenNav

@Serializable
object ChessStreamersScreenNav

@Serializable
data class DetailPlayerScreenNav(val username: String)