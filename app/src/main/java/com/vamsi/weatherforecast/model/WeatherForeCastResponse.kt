package com.vamsi.weatherforecast.model


import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.Deferred
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

data class WeatherForeCastResponse(
    val city: City,
    val cnt: Int, // 40
    val cod: String, // 200
    val list: List<Week>,
    val message: Int // 0
)

