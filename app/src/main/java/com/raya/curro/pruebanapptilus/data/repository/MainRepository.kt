package com.raya.curro.pruebanapptilus.data.repository

import com.raya.curro.pruebanapptilus.data.api.RestApi

class MainRepository(private val restApi: RestApi) {
    suspend fun getOompaLoompas(page: Int) = restApi.getOompaLoompa(page)
    suspend fun getOompaLoompasDetail(id: Int) = restApi.getOompaLoompaDetail(id)
}