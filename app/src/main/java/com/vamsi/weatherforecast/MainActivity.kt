package com.vamsi.weatherforecast

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.vamsi.weatherforecast.ui.cities.CityListFragment
import com.vamsi.weatherforecast.ui.weatherforecast.WeatherForeCastFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    var permissionStatus = false
    private val REQUEST_ID_MULTIPLE_PERMISSIONS = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED) {
                permissionStatus = checkAndRequestPermissions()
            }
        }

        nav_view.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    fun checkAndRequestPermissions(): Boolean {
        val location =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val coarseLocation =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        val listPermissionsNeeded: MutableList<String> = ArrayList()
        if (location != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        if (coarseLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(
                this, listPermissionsNeeded.toTypedArray(), REQUEST_ID_MULTIPLE_PERMISSIONS)
            return false
        }
        return true
    }


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            R.id.navigation_current -> {
                val fragment = WeatherForeCastFragment()
                supportFragmentManager.beginTransaction().replace(
                    R.id.container, fragment, fragment.javaClass.getSimpleName()).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_cities -> {
                val fragment = CityListFragment()
                supportFragmentManager.beginTransaction().replace(
                    R.id.container,
                    fragment,
                    fragment.javaClass.getSimpleName()
                ).addToBackStack(null)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }


}


