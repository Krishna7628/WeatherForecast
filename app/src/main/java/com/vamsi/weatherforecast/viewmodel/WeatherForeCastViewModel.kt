package com.vamsi.weatherforecast.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vamsi.weatherforecast.model.WeatherForeCastResponse
import com.vamsi.weatherforecast.newtwork.APIService
import com.vamsi.weatherforecast.newtwork.RetrofitInstance.buildService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherForeCastViewModel: ViewModel() {
    var weatherForeCastResponse: MutableLiveData<WeatherForeCastResponse> = MutableLiveData()


    fun getWeatherForeCastObserver(): MutableLiveData<WeatherForeCastResponse>? {
        return weatherForeCastResponse
    }
    fun makeApiCall(lat: String, lon: String) {              // making call based on lat lon
        val request = buildService(APIService::class.java)
        val call = request.getWeatherForeCast(lat, lon)

        call.enqueue(object : Callback<WeatherForeCastResponse> {
            override fun onResponse(call: Call<WeatherForeCastResponse>, response: Response<WeatherForeCastResponse>) {
                if (response.isSuccessful) {
                    weatherForeCastResponse?.postValue(response.body())

                }
            }

            override fun onFailure(call: Call<WeatherForeCastResponse>, t: Throwable) {
                weatherForeCastResponse?.postValue(null)
            }
        })
    }
}



