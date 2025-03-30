package com.example.chesstats.presentation.detailPlayerScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chesstats.data.models.FlagModel
import com.example.chesstats.data.repositories.ChessRepositoryImp
import com.example.chesstats.domain.models.PlayerDomainModel
import com.example.chesstats.utils.ResultCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailPlayerViewModel @Inject constructor(
    private val chessRepositoryImp: ChessRepositoryImp
) : ViewModel() {

    var player = MutableStateFlow<PlayerDomainModel?>(null)

    var loading = MutableStateFlow<Boolean>(false)

    var error = MutableStateFlow<String>("")

    fun searchPlayer(username: String) {
        loading.value = true
        viewModelScope.launch {

            val response = chessRepositoryImp.getPlayerInfo(username)

            when (response) {
                is ResultCase.Error -> {
                    if (!response.message.isNullOrEmpty()) {
                        error.value = response.message
                    }
                }

                is ResultCase.Success -> {
                    player.value = response.data
                    getFlag()
                }
            }

            loading.value = false

        }
    }

    fun getFlag() {
        viewModelScope.launch {

            val response = chessRepositoryImp.getCountryFromPlayer(player.value?.country ?: "")

            when (response) {
                is ResultCase.Error -> {
                    if (!response.message.isNullOrEmpty()) {
                        error.value = response.message
                    }
                }

                is ResultCase.Success -> {
                    player.value = player.value?.copy(countryName = response.data.name)

                }
            }
        }
    }

    fun cleanError() {
        error.value = ""
    }
}