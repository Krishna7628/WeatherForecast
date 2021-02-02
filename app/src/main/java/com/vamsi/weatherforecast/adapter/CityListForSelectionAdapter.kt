package com.vamsi.weatherforecast.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.vamsi.weatherforecast.R
import com.vamsi.weatherforecast.data.UserPreferences

class CityListForSelectionAdapter(var context: Context, var cityList: MutableList<String>) :
    RecyclerView.Adapter<CityListForSelectionAdapter.MyViewHolder>() {

    var count :Int = 0

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var city: CheckBox = view.findViewById(R.id.cb_city)
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.city_item,
            parent,
            false
        )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val cityName = cityList[position]
        holder.city.text = cityName

        holder.city.setOnClickListener(View.OnClickListener {
            if(count < 8){
                checkWhereToInsertOrRemoveCityName(cityName)
            }else{
                holder.city.isChecked = false
            }

        })

    }

    private fun checkWhereToInsertOrRemoveCityName(selectedCityName: String) {

        var userPreferences = UserPreferences(context)
        var selectedCities = userPreferences.getCities() as MutableList<String>

        if(selectedCities.size <= 7) {
            if (selectedCities.contains(selectedCityName)) {
                selectedCities.remove(selectedCityName)
            } else {
                selectedCities.add(selectedCityName)
            }

            userPreferences.setCities<String>(selectedCities)
            count = selectedCities.size

        }else if(selectedCities.size == 7 ){
            count = 7
        }
    }

    override fun getItemCount(): Int {
        return cityList.size
    }
}