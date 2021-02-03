package com.vamsi.weatherforecast.newtwork

import androidx.lifecycle.MutableLiveData
import com.vamsi.weatherforecast.model.WeatherForeCastResponse
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("data/2.5/forecast?")
    fun getWeatherForeCast(@Query("lat")lat : String, @Query("lon") lon : String, @Query("appid") appid :String = "5db0abcffc204cab75495c4f943eba5e") : Call<WeatherForeCastResponse>

}