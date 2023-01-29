package com.raya.curro.pruebanapptilus.data.api

import com.raya.curro.pruebanapptilus.data.model.OompaLoompaResponse
import com.raya.curro.pruebanapptilus.data.model.Results

class RestApiImpl(private val restApiService: RestApiService): RestApi {

    override suspend fun getOompaLoompa(page: Int): OompaLoompaResponse {
        return restApiService.getOompaLoompa(page = page)
    }

    override suspend fun getOompaLoompaDetail(id: Int): Results {
        return restApiService.getOompaLoompaDetail(id = id)
    }
}