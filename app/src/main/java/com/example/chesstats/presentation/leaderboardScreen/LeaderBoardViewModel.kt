package com.example.chesstats.presentation.leaderboardScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chesstats.data.models.LeaderBoardModel
import com.example.chesstats.data.repositories.ChessRepositoryImp
import com.example.chesstats.domain.repositories.ChessRepository
import com.example.chesstats.utils.ResultCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeaderBoardViewModel @Inject constructor(
    private val chessRepositoryImp: ChessRepository
) : ViewModel() {

    var leaderBoardLists = MutableStateFlow<LeaderBoardModel?>(null)

    private var _loading = MutableStateFlow<Boolean>(false)
    val loading: StateFlow<Boolean> = _loading

    private var _error = MutableStateFlow<String>("")
    val error: StateFlow<String> = _error

    init {
        getLeaderBoards()
    }

    private fun getLeaderBoards() {
        _loading.value = true

        viewModelScope.launch {
            val leaderBoardResponse = chessRepositoryImp.getLeaderBoards()

            when (leaderBoardResponse) {
                is ResultCase.Error -> {
                    if (!leaderBoardResponse.message.isNullOrEmpty()){
                        _error.value = leaderBoardResponse.message
                    }
                }
                is ResultCase.Success -> {
                    leaderBoardLists.value = leaderBoardResponse.data
                }
            }

            _loading.value = false
        }
    }

    fun cleanError(){
        _error.value = ""
    }
}