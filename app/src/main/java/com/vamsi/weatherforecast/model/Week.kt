package com.vamsi.weatherforecast.model


import com.google.gson.annotations.SerializedName

data class Week(
    val clouds: Clouds,
    val dt: Int, // 1612321200
    @SerializedName("dt_txt")
    val dtTxt: String, // 2021-02-03 03:00:00
    val main: Main,
    val pop: Int, // 0
    val sys: Sys,
    val visibility: Int, // 10000
    val weather: List<Weather>,
    val wind: Wind
)