package com.raya.curro.pruebanapptilus.data.api

import com.raya.curro.pruebanapptilus.data.model.OompaLoompaResponse
import com.raya.curro.pruebanapptilus.data.model.Results

interface RestApi {
    suspend fun getOompaLoompa(page: Int): OompaLoompaResponse
    suspend fun getOompaLoompaDetail(id: Int): Results
}