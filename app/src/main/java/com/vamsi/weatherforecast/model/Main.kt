package com.vamsi.weatherforecast.model


import com.google.gson.annotations.SerializedName

data class Main(
    @SerializedName("feels_like")
    val feelsLike: Double, // 295.84
    @SerializedName("grnd_level")
    val grndLevel: Int, // 1016
    val humidity: Int, // 80
    val pressure: Int, // 1016
    @SerializedName("sea_level")
    val seaLevel: Int, // 1016
    val temp: Double, // 294.73
    @SerializedName("temp_kf")
    val tempKf: Double, // -0.6
    @SerializedName("temp_max")
    val tempMax: Double, // 295.33
    @SerializedName("temp_min")
    val tempMin: Double // 294.73
)