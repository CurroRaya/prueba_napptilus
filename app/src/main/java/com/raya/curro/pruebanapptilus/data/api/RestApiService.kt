package com.raya.curro.pruebanapptilus.data.api

import com.raya.curro.pruebanapptilus.data.model.OompaLoompaResponse
import com.raya.curro.pruebanapptilus.data.model.Results
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RestApiService {
    @GET("oompa-loompas")
    suspend fun getOompaLoompa(
        @Query("page") page: Int
    ): OompaLoompaResponse

    @GET("oompa-loompas/{id}")
    suspend fun getOompaLoompaDetail(
        @Path("id") id: Int
    ): Results
}