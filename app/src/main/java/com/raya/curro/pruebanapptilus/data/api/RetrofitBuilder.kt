package com.raya.curro.pruebanapptilus.data.api

import com.raya.curro.pruebanapptilus.data.api.utils.ApiConectionUtils
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.OkHttpClient

object RetrofitBuilder {

    private fun getRetrofit(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        return  Retrofit.Builder()
            .baseUrl(ApiConectionUtils.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    val apiService: RestApiService = getRetrofit().create(RestApiService::class.java)
}