package com.raya.curro.pruebanapptilus.intent.state

import com.raya.curro.pruebanapptilus.data.model.Results

sealed class DetailState {
    object Idle: DetailState()
    object Loading: DetailState()
    data class LoadTasks(val oompaLoompa: Results): DetailState()
    data class Error(val error: String?): DetailState()
}
