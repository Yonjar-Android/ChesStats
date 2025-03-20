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

    init {
        viewModelScope.launch {
            player.value = chessRepositoryImp.getPlayerInfo("annacramling")
        }
    }
}