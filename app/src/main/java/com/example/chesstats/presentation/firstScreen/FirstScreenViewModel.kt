package com.example.chesstats.presentation.firstScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chesstats.data.models.CountryModel
import com.example.chesstats.data.repositories.ChessRepositoryImp
import com.example.chesstats.domain.models.PlayerDomainModel
import com.example.chesstats.utils.ResultCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FirstScreenViewModel @Inject constructor(
    private val chessRepositoryImp: ChessRepositoryImp
) : ViewModel() {

    private var _player = MutableStateFlow<PlayerDomainModel?>(null)
    val player: StateFlow<PlayerDomainModel?> = _player

    private var _loading = MutableStateFlow<Boolean>(false)
    val loading: StateFlow<Boolean> = _loading

    private var _error = MutableStateFlow<String>("")
    val error: StateFlow<String> = _error

    init {
        viewModelScope.launch {
            searchPlayer("magnuscarlsen")
            chessRepositoryImp.getLeaderBoards()
        }
    }

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

                is ResultCase.Success<PlayerDomainModel> -> {
                    _player.value = response.data
                    getCountry()
                }

            }
            _loading.value = false
        }
    }

    fun getCountry() {
        viewModelScope.launch {

            val response = chessRepositoryImp.getCountryFromPlayer(_player.value?.country ?: "")

            when (response) {
                is ResultCase.Error -> {
                    if (!response.message.isNullOrEmpty()) {
                        _error.value = response.message
                    }
                }

                is ResultCase.Success<CountryModel> -> {
                    _player.value = _player.value?.copy(
                        countryName = response.data.name
                    )
                }
            }
        }
    }

    fun cleanError(){
        _error.value = ""
    }
}