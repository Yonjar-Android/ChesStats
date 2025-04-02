package com.example.chesstats.presentation.detailPlayerScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chesstats.data.models.FlagModel
import com.example.chesstats.data.repositories.ChessRepositoryImp
import com.example.chesstats.domain.models.PlayerDomainModel
import com.example.chesstats.domain.repositories.ChessRepository
import com.example.chesstats.utils.ResultCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailPlayerViewModel @Inject constructor(
    private val chessRepositoryImp: ChessRepository
) : ViewModel() {

    private var _player = MutableStateFlow<PlayerDomainModel?>(null)
    val player: StateFlow<PlayerDomainModel?> = _player

    private var _loading = MutableStateFlow<Boolean>(false)
    val loading: StateFlow<Boolean> = _loading

    private var _error = MutableStateFlow<String>("")
    val error: StateFlow<String> = _error

    fun searchPlayer(username: String) {
        _loading.value = true
        viewModelScope.launch {

            val response = chessRepositoryImp.getPlayerInfo(username)

            when (response) {
                is ResultCase.Error -> {
                    if (!response.message.isNullOrEmpty()) {
                        _error.value = response.message
                    }
                }

                is ResultCase.Success -> {
                    _player.value = response.data
                    getCountry()
                }
            }

            _loading.value = false

        }
    }

    fun getCountry() {
        viewModelScope.launch {

            val response = chessRepositoryImp.getCountryFromPlayer(player.value?.country ?: "")

            when (response) {
                is ResultCase.Error -> {
                    if (!response.message.isNullOrEmpty()) {
                        _error.value = response.message
                    }
                }

                is ResultCase.Success -> {
                    _player.value = player.value?.copy(countryName = response.data?.name ?: "")

                }
            }
        }
    }

    fun cleanError() {
        _error.value = ""
    }
}