package com.raya.curro.pruebanapptilus.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raya.curro.pruebanapptilus.data.api.RestApi
import com.raya.curro.pruebanapptilus.data.model.Results
import com.raya.curro.pruebanapptilus.intent.DetailIntent
import com.raya.curro.pruebanapptilus.intent.state.DetailState
import com.raya.curro.pruebanapptilus.intent.state.MainState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch


class DetailViewModel(private val repository: RestApi) : ViewModel() {

    var _character: MutableLiveData<Results?> = MutableLiveData<Results?>()
    val characterItem: LiveData<Results?>
        get() = _character

    val userIntent = Channel<DetailIntent>(Channel.UNLIMITED)
    private val _detailState = MutableStateFlow<DetailState>(DetailState.Idle)

    val detailState: StateFlow<DetailState>
        get() = _detailState

    var _id: Int = 0

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when(it) {
                    is DetailIntent.FetchOompaLoompaDetail -> {
                        fetchDetail()
                    }
                }
            }
        }
    }

    private suspend fun fetchDetail() {
        viewModelScope.launch {
            _detailState.value = DetailState.Loading

            _detailState.value = try {
                DetailState.LoadTasks(repository.getOompaLoompaDetail(_id))
            } catch (e: Exception) {
                DetailState.Error(e.message)
            }
        }
    }

    fun setIdleFragment() {
        viewModelScope.launch {
            _detailState.value = DetailState.Idle
        }
    }

    fun setId(id: Int){
        _id = id
    }
}