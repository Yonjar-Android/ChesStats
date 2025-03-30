package com.example.chesstats.presentation.leaderboardScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chesstats.data.models.ProfileDataModel
import com.example.chesstats.data.repositories.ChessRepositoryImp
import com.example.chesstats.utils.ResultCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeaderBoardViewModel @Inject constructor(
    private val chessRepositoryImp: ChessRepositoryImp
) : ViewModel() {

    var blitzPlayers = MutableStateFlow<List<ProfileDataModel>?>(emptyList())
    var rapidPlayers = MutableStateFlow<List<ProfileDataModel>?>(emptyList())
    var bulletPlayers = MutableStateFlow<List<ProfileDataModel>?>(emptyList())

    var loading = MutableStateFlow<Boolean>(false)

    var error = MutableStateFlow<String>("")

    init {
        getLeaderBoards()
    }

    private fun getLeaderBoards() {
        loading.value = true

        viewModelScope.launch {
            val leaderBoardResponse = chessRepositoryImp.getLeaderBoards()

            when (leaderBoardResponse) {
                is ResultCase.Error -> {
                    if (!leaderBoardResponse.message.isNullOrEmpty()){
                        error.value = leaderBoardResponse.message
                    }
                }
                is ResultCase.Success -> {
                    blitzPlayers.value = leaderBoardResponse.data.blitz
                    rapidPlayers.value = leaderBoardResponse.data.rapid
                    bulletPlayers.value = leaderBoardResponse.data.bullet
                }
            }

            loading.value = false
        }
    }

    fun cleanError(){
        error.value = ""
    }
}