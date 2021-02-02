package com.vamsi.weatherforecast.ui.weatherforecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.vamsi.weatherforecast.R


/**
 * A simple [Fragment] subclass.
 * Use the [CurrentWeatherConditionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CurrentWeatherConditionFragment : Fragment() {

    private val tvMainTemp: TextView? = null
    private  var tvMaxTemp:TextView? = null
    private  var tvMinTemp:TextView? = null
    private  var tvWeatherDescription:TextView? = null
    private  var tvWindSpeed:TextView? = null
    private val rvNextWeatherReport: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(
            R.layout.fragment_current_weather_condition,
            container,
            false
        )


        val bundle = arguments
        if(bundle!= null) {
            val cityName = bundle!!.getSerializable("cityName")
        }
        return rootView
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}