package com.example.chesstats.presentation.chessStreamersScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chesstats.data.models.streamers.StreamerModel
import com.example.chesstats.data.repositories.ChessRepositoryImp
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
            try {
                loading.value = true

                streamers.value = chessRepositoryImp.getStreamers() ?: emptyList<StreamerModel>()

            } catch (e: Exception) {
                println(e.message)
            } finally {
                loading.value = false
            }
        }
    }
}