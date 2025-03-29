package com.example.chesstats.utils

sealed class ResultCase<out T> {
    data class Success<out T>(val data: T) : ResultCase<T>()

    data class Error(val message: String? = null) : ResultCase<Nothing>()
}