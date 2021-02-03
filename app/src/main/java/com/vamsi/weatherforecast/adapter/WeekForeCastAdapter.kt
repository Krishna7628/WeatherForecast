package com.vamsi.weatherforecast.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vamsi.weatherforecast.R
import com.vamsi.weatherforecast.model.Week

class WeekForeCastAdapter(var list: List<Week>) :
    RecyclerView.Adapter<WeekForeCastAdapter.MyViewHolder>() {


    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var currentTemp: TextView = view.findViewById(R.id.currentTemp)
        var dateTime: TextView = view.findViewById(R.id.dateTime)
        var maxTemp: TextView = view.findViewById(R.id.maxTemp)
        var minTemp: TextView = view.findViewById(R.id.minTemp)
        var windSpeed: TextView = view.findViewById(R.id.windSpeed)
        var description: TextView = view.findViewById(R.id.description)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WeekForeCastAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.week_details_item,
            parent,
            false
        )
        return MyViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: WeekForeCastAdapter.MyViewHolder, position: Int) {
        val day = list[position]
        holder.currentTemp.text = day.main.temp.toString()+ "k"
        holder.maxTemp.text = day.main.tempMax.toString()+ "k"
        holder.minTemp.text = day.main.tempMin.toString()+ "k"
        holder.windSpeed.text = day.wind.speed.toString()
        holder.dateTime.text = day.dtTxt.toString()
        holder.description.text = day.weather.get(0).description.toString()
    }

    override fun getItemCount(): Int {
        return list.size
    }
}