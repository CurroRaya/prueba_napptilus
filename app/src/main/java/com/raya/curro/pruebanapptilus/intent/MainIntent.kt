package com.raya.curro.pruebanapptilus.intent

sealed class MainIntent {
    object FetchOompaLoompas: MainIntent()
    object GetMoreOompaLoompas: MainIntent()
}