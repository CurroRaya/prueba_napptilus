package com.raya.curro.pruebanapptilus.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raya.curro.pruebanapptilus.data.repository.MainRepository
import com.raya.curro.pruebanapptilus.intent.MainIntent
import com.raya.curro.pruebanapptilus.intent.state.MainState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class ListViewModel(private val repository: MainRepository) : ViewModel() {

    val userIntent = Channel<MainIntent>(Channel.UNLIMITED)
    private val _mainState = MutableStateFlow<MainState>(MainState.Idle)

    private var page = 0
    private var incrementPage = 1

    val mainState: StateFlow<MainState>
        get() = _mainState

    init {
        handleIntent()
    }


    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when(it) {
                    is MainIntent.FetchOompaLoompas -> {
                        fetchCharacters()
                    }
                    is MainIntent.GetMoreOompaLoompas -> {
                        page += incrementPage
                        fetchCharacters()
                    }
                }
            }
        }
    }

    private suspend fun fetchCharacters() {
        viewModelScope.launch {
            _mainState.value = MainState.Loading

            _mainState.value = try {
                MainState.LoadTasks(repository.getOompaLoompas(page))
            } catch (e: Exception) {
                MainState.Error(e.message)
            }
        }
    }

    fun setIdleFragment() {
        viewModelScope.launch {
            _mainState.value = MainState.Idle
        }
    }
}