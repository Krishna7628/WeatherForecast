package com.vamsi.weatherforecast.newtwork

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val API_KEY = "5db0abcffc204cab75495c4f943eba5e"

const val BASE_URL = "http://api.openweathermap.org/"

object RetrofitInstance {
    private val client = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    fun <T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }
}