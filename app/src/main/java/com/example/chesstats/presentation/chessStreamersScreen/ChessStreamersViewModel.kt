package com.example.chesstats.presentation.chessStreamersScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chesstats.data.models.streamers.StreamerModel
import com.example.chesstats.domain.repositories.ChessRepository
import com.example.chesstats.utils.ResultCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChessStreamersViewModel @Inject constructor(
    private val chessRepositoryImp: ChessRepository
) : ViewModel() {

    val streamers = MutableStateFlow<List<StreamerModel>>(emptyList())

    private var _loading = MutableStateFlow<Boolean>(false)
    val loading: StateFlow<Boolean> = _loading

    private var _error = MutableStateFlow<String>("")
    val error: StateFlow<String> = _error

    init {
        getStreamers()
    }

    private fun getStreamers() {
        _loading.value = true
        viewModelScope.launch {

            val response = chessRepositoryImp.getStreamers()

            when (response){
                is ResultCase.Error -> {
                    if (!response.message.isNullOrEmpty()) _error.value = response.message
                }
                is ResultCase.Success -> {
                    streamers.value = response.data ?: listOf()
                }
            }

            _loading.value = false

        }
    }

    fun cleanError(){
        _error.value = ""
    }
}