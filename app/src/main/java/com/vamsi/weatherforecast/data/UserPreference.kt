package com.vamsi.weatherforecast.data

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class UserPreferences(context: Context) {

   val PREFERENCES ="WeatherForecast"

    private var sharedPreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)

    fun setStringPref(key: String?, value: String?) {
        sharedPreferences?.edit()?.putString(key, value)?.apply()
    }

    fun <T> setCities(dcIds: List<String?>?) {
        val gson = Gson()
        val json = gson.toJson(dcIds)
        sharedPreferences?.edit()?.putString("CityList", json)?.apply();
    }

    fun getCities(): List<String>? {
        val gson = Gson()
        var citys: List<String> = mutableListOf();
        val list = sharedPreferences!!.getString("CityList", "")
        val type = object : TypeToken<List<String?>?>() {}.type

       if(!list.isNullOrBlank()){ citys = gson.fromJson(list, type)}
        return citys
    }

}
