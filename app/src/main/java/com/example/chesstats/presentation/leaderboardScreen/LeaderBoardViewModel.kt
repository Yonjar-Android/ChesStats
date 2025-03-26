package com.example.chesstats.presentation.leaderboardScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chesstats.data.models.ProfileDataModel
import com.example.chesstats.data.repositories.ChessRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeaderBoardViewModel @Inject constructor(
    private val chessRepositoryImp: ChessRepositoryImp
): ViewModel() {

    var blitzPlayers = MutableStateFlow<List<ProfileDataModel>?>(emptyList())
    var rapidPlayers = MutableStateFlow<List<ProfileDataModel>?>(emptyList())
    var bulletPlayers = MutableStateFlow<List<ProfileDataModel>?>(emptyList())

    var loading = MutableStateFlow<Boolean>(false)

    init {
        getLeaderBoards()
    }

    private fun getLeaderBoards(){
        loading.value = true

        viewModelScope.launch {
            try {
                val leaderBoard = chessRepositoryImp.getLeaderBoards()
                blitzPlayers.value = leaderBoard?.blitz
                rapidPlayers.value = leaderBoard?.rapid
                bulletPlayers.value = leaderBoard?.bullet

            } catch (e: Exception){
                println(e.message)
            } finally {
                loading.value = false
            }
        }
    }
}