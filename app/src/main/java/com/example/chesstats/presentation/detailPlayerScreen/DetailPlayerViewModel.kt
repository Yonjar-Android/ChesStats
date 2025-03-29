package com.example.chesstats.presentation.detailPlayerScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chesstats.data.models.FlagModel
import com.example.chesstats.data.repositories.ChessRepositoryImp
import com.example.chesstats.domain.models.PlayerDomainModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailPlayerViewModel @Inject constructor(
    private val chessRepositoryImp: ChessRepositoryImp
): ViewModel() {

    var player = MutableStateFlow<PlayerDomainModel?>(null)

    var flagValue = MutableStateFlow<FlagModel?>(null)

    var loading = MutableStateFlow<Boolean>(false)

    fun searchPlayer(username:String){
        loading.value = true
        viewModelScope.launch{
            try{
                val playerSearch = chessRepositoryImp.getPlayerInfo(username)

                if(playerSearch != null){
                    player.value = playerSearch
                    getFlag()
                }
            } catch(e:Exception){
                println(e.message)
            }
            finally {
                loading.value = false
            }
        }
    }

    fun getFlag() {
        viewModelScope.launch {
            try {
                val country = chessRepositoryImp.getCountryFromPlayer(player.value?.country ?: "")
                if (country != null) {
                    val flagResponse = chessRepositoryImp.getFlag(country.name)
                    if (flagResponse != null) {
                        flagValue.value = flagResponse
                    }
                }

            } catch (e: Exception) {
                println(e.message)
            }
        }
    }

}