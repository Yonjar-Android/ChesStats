package com.example.chesstats.data.models

import com.google.gson.annotations.SerializedName

data class ChessStats(
    @SerializedName("chess_rapid") val chessRapid: ChessMode?,
    @SerializedName("chess_bullet") val chessBullet: ChessMode?,
    @SerializedName("chess_blitz") val chessBlitz: ChessMode?,
    val fide: Int?
)

data class ChessMode(
    val last: ChessLast?,
    val best: ChessBest?,
    val record: ChessRecord?
)

data class ChessLast(
    val rating: Int,
    val date: Long
)

data class ChessBest(
    val rating: Int,
    val date: Long,
    val game: String?
)

data class ChessRecord(
    val win: Int,
    val loss: Int,
    val draw: Int
)
