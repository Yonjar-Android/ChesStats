package com.example.chesstats.presentation.firstScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chesstats.data.repositories.ChessRepositoryImp
import com.example.chesstats.domain.models.PlayerDomainModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FirstScreenViewModel @Inject constructor(
    private val chessRepositoryImp: ChessRepositoryImp
): ViewModel() {

    var player = MutableStateFlow<PlayerDomainModel?>(null)

    var loading = MutableStateFlow<Boolean>(false)

    init {
        viewModelScope.launch {
            searchPlayer("magnuscarlsen")
            chessRepositoryImp.getLeaderBoards()
        }
    }

    fun searchPlayer(username:String){
        loading.value = true
        viewModelScope.launch{
            try{
                val playerSearch = chessRepositoryImp.getPlayerInfo(username)

                if(playerSearch != null){
                    player.value = playerSearch
                }
            } catch(e:Exception){
                println(e.message)
            }
            finally {
                resetLoad()
            }
        }
    }

    fun resetLoad(){
        loading.value = false
    }
}