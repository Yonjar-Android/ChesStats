package com.example.chesstats.presentation.chessStreamersScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chesstats.data.models.streamers.StreamerModel
import com.example.chesstats.data.repositories.ChessRepositoryImp
import com.example.chesstats.utils.ResultCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChessStreamersViewModel @Inject constructor(
    private val chessRepositoryImp: ChessRepositoryImp
) : ViewModel() {

    val streamers = MutableStateFlow<List<StreamerModel>>(emptyList())

    val loading = MutableStateFlow<Boolean>(false)

    init {
        getStreamers()
    }

    private fun getStreamers() {
        viewModelScope.launch {
            loading.value = true

            val response = chessRepositoryImp.getStreamers()

            when (response){
                is ResultCase.Error -> {

                }
                is ResultCase.Success -> {
                    streamers.value = response.data
                }
            }

            loading.value = false

        }
    }
}