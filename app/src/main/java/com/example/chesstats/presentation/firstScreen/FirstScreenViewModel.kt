package com.example.chesstats.presentation.firstScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chesstats.data.models.CountryModel
import com.example.chesstats.data.models.FlagModel
import com.example.chesstats.data.repositories.ChessRepositoryImp
import com.example.chesstats.domain.models.PlayerDomainModel
import com.example.chesstats.utils.ResultCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FirstScreenViewModel @Inject constructor(
    private val chessRepositoryImp: ChessRepositoryImp
) : ViewModel() {

    var player = MutableStateFlow<PlayerDomainModel?>(null)

    var flagValue = MutableStateFlow<FlagModel?>(null)

    var loading = MutableStateFlow<Boolean>(false)

    init {
        viewModelScope.launch {
            searchPlayer("magnuscarlsen")
            chessRepositoryImp.getLeaderBoards()
        }
    }

    fun searchPlayer(username: String) {
        loading.value = true
        viewModelScope.launch {

            val response = chessRepositoryImp.getPlayerInfo(username)

            when (response) {
                is ResultCase.Error -> {

                }

                is ResultCase.Success<PlayerDomainModel> -> {
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

            when(response){
                is ResultCase.Error -> {

                }
                is ResultCase.Success<CountryModel> -> {
                    val flagResponse = chessRepositoryImp.getFlag(response.data.name)
                    when(flagResponse){
                        is ResultCase.Error -> {

                        }
                        is ResultCase.Success -> {
                            flagValue.value = flagResponse.data
                        }
                    }
                }
            }
        }
    }
}