package com.vamsi.weatherforecast.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.vamsi.weatherforecast.R
import com.vamsi.weatherforecast.ui.weatherforecast.WeatherForeCastFragment


class ListCitiesAdapter(var context: Context, var cityList: MutableList<String>) :
RecyclerView.Adapter<ListCitiesAdapter.MyViewHolder>() {


    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var cityName: TextView = view.findViewById(R.id.tv_city_name)
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.city_name_item,
            parent,
            false
        )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.cityName.text = cityList[position]

        holder.cityName.setOnClickListener(View.OnClickListener {
            val fragment = WeatherForeCastFragment() //  object of next fragment

            val bundle = Bundle()
            bundle.putString("cityName", cityList[position])
            fragment.setArguments(bundle)
            val fragmentManager: FragmentManager = (context as FragmentActivity).supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.remove( WeatherForeCastFragment())
            fragmentTransaction.replace(R.id.container, fragment)
            fragmentTransaction.commit()

        })

    }



    override fun getItemCount(): Int {
        return cityList.size
    }
}


