package com.vamsi.weatherforecast.ui.weatherforecast

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.vamsi.weatherforecast.R
import com.vamsi.weatherforecast.adapter.WeekForeCastAdapter
import com.vamsi.weatherforecast.model.WeatherForeCastResponse
import com.vamsi.weatherforecast.viewmodel.WeatherForeCastViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_current_weather_condition.*
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [WeatherForeCastFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WeatherForeCastFragment : Fragment() {
    var permissionStatus = false
    private val REQUEST_ID_MULTIPLE_PERMISSIONS = 1
    private var locationManager: LocationManager? = null

    private var selectedLocation: Location? = null

    @SuppressLint("VisibleForTests")
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

//        (activity as MainActivity).nav_view.menu.get(0)

        locationManager =
            this.requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val bundle = arguments
        var cityName = ""
        if (bundle != null) {
            //getting selected city name from city list
            cityName = bundle!!.getSerializable("cityName") as String
            setWeatherReport(cityName)
        } else {
            setWeatherReport(cityName)
        }
        return rootView
    }

    private fun setWeatherReport(cityName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permissionStatus = checkAndRequestPermissions()

            if (permissionStatus) {
                getLocation(cityName)
            } else {
                displayAlert("Please accept the permission")
            }
        } else {
            displayAlert("Your mobile version is to low")
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation(cityName: String) {
        var latitude = ""
        var longitude = ""

        if (cityName.isEmpty()) {

            val isGPSEnabled = locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
            val isNetworkEnabled =
                locationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

            if (isGPSEnabled || isNetworkEnabled) {

                var locationGps: Location? = null
                var locationNetwork: Location? = null
                if (isGPSEnabled) {
                    locationManager!!.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        10000,
                        0f,
                        object : LocationListener {
                            override fun onLocationChanged(location: Location?) {
                                if (location != null) {
                                    locationGps = location
                                }
                            }

                            override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
                                TODO("Not yet implemented")
                            }

                            override fun onProviderEnabled(p0: String?) {
                                TODO("Not yet implemented")
                            }

                            override fun onProviderDisabled(p0: String?) {
                                TODO("Not yet implemented")
                            }
                        })

                    val lastUpdateLocationFromGPS =
                        locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    if (lastUpdateLocationFromGPS != null) {
                        locationGps = lastUpdateLocationFromGPS
                    }

                    if (isNetworkEnabled) {
                        locationManager!!.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            10000,
                            0f,
                            object : LocationListener {
                                override fun onLocationChanged(location: Location?) {
                                    if (location != null) {
                                        locationNetwork = location
                                    }
                                }

                                override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
                                    TODO("Not yet implemented")
                                }

                                override fun onProviderEnabled(p0: String?) {
                                    TODO("Not yet implemented")
                                }

                                override fun onProviderDisabled(p0: String?) {
                                    TODO("Not yet implemented")
                                }
                            })

                        val lastUpdateLocationFromNetwork =
                            locationManager!!.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                        if (lastUpdateLocationFromNetwork != null) {
                            locationNetwork = lastUpdateLocationFromNetwork
                        }
                    }


                    if (locationGps != null && locationNetwork != null) {
                        if (locationGps!!.accuracy > locationGps!!.accuracy) {
                            latitude = locationNetwork!!.latitude.toString()
                            longitude = locationNetwork!!.longitude.toString()
                        } else {
                            latitude = locationGps!!.latitude.toString()
                            longitude = locationGps!!.longitude.toString()
                        }
                    } else {
                        if (locationGps != null) {
                            latitude = locationGps!!.latitude.toString()
                            longitude = locationGps!!.longitude.toString()
                        } else if (locationNetwork != null) {
                            latitude = locationNetwork!!.latitude.toString()
                            longitude = locationNetwork!!.longitude.toString()
                        }
                    }

                }

            } else {
                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
        } else {
            val geocoder = Geocoder(this.requireContext());
            val list = geocoder.getFromLocationName(cityName, 1);
            if (list.size > 0) {
                latitude = list.get(0).latitude.toString()
                longitude = list.get(0).longitude.toString()
            }
        }




        var weatherForeCastViewModel = ViewModelProvider(this).get(WeatherForeCastViewModel::class.java)
        weatherForeCastViewModel.getWeatherForeCastObserver()?.observe(
            this.requireActivity(),
            Observer<WeatherForeCastResponse> { weatherForeCastResponse ->
                if (weatherForeCastResponse != null) {

                    tv_temperature_label.text = weatherForeCastResponse.city.name        //city name
                    tv_current_temp.text = weatherForeCastResponse.list.get(0).main.temp.toString()
                    tv_max_temp.text = weatherForeCastResponse.list.get(0).main.tempMax.toString()
                    tv_min_temp.text = weatherForeCastResponse.list.get(0).main.tempMax.toString()
                    tv_weather_description.text =
                        weatherForeCastResponse.list.get(0).weather.get(0).description.toString()
                    tv_wind_speed.text = weatherForeCastResponse.list.get(0).wind.speed.toString()


                    val WeekWorCastAdapter = WeekForeCastAdapter(weatherForeCastResponse.list)
                    rv_list_days.layoutManager = LinearLayoutManager(this.requireContext())
                    rv_list_days.adapter = WeekWorCastAdapter
                    rv_list_days.setNestedScrollingEnabled(false)
                } else {
                    displayAlert("Something when wrong !")
                }
            })
        weatherForeCastViewModel.makeApiCall(latitude, longitude)

    }

    private fun displayAlert(msg: String) {
        val builder = AlertDialog.Builder(this.requireContext())
        builder.setMessage(msg)
        builder.setPositiveButton("Ok") { dialogInterface, i ->
        }
        builder.setCancelable(false)
        builder.show()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }


    fun checkAndRequestPermissions(): Boolean {
        val location =
            ContextCompat.checkSelfPermission(
                this.requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        val coarseLocation =
            ContextCompat.checkSelfPermission(
                this.requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        val listPermissionsNeeded: MutableList<String> = ArrayList()
        if (location != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        if (coarseLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(
                this.requireActivity(),
                listPermissionsNeeded.toTypedArray(),
                REQUEST_ID_MULTIPLE_PERMISSIONS
            )
            return false
        }
        return true
    }


}
