package com.raya.curro.pruebanapptilus.intent.state

import com.raya.curro.pruebanapptilus.data.model.OompaLoompaResponse

sealed class MainState {
    object Idle: MainState()
    object Loading: MainState()
    data class LoadTasks(val oompaLoompas: OompaLoompaResponse): MainState()
    data class Error(val error: String?): MainState()
}
