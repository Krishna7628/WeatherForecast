package com.vamsi.weatherforecast.model


import com.google.gson.annotations.SerializedName

data class City(
    val coord: Coord,
    val country: String, // IN
    val id: Int, // 1254953
    val name: String, // Tanuku
    val population: Int, // 68290
    val sunrise: Int, // 1612314236
    val sunset: Int, // 1612355412
    val timezone: Int // 19800
    )