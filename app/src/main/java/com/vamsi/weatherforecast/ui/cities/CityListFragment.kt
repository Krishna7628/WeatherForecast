package com.vamsi.weatherforecast.ui.cities

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.vamsi.weatherforecast.R
import com.vamsi.weatherforecast.adapter.CityListForSelectionAdapter
import com.vamsi.weatherforecast.adapter.ListCitiesAdapter
import com.vamsi.weatherforecast.data.UserPreferences
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [CityListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CityListFragment : Fragment() {

    private lateinit var rvCityList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_city_list, container, false)

        rvCityList = rootView.findViewById(R.id.rv_list_cities);
        val userPreferences = UserPreferences(this.requireContext())
        val selectedCities = userPreferences.getCities() as MutableList<String>
        if (selectedCities.isEmpty() && selectedCities.size < 3) {
            displayBottomSheetForCitySelection()
        } else {

         displaySelectedCityList(selectedCities)
        }
        return rootView;
    }

    private fun displaySelectedCityList(selectedCities: MutableList<String>) {
        val listCitiesAdapter = ListCitiesAdapter(this.requireActivity(),selectedCities)
        rvCityList.layoutManager = LinearLayoutManager(this.requireContext())
        rvCityList.adapter = listCitiesAdapter
    }

    private fun displayBottomSheetForCitySelection() {
        val bottomSheetView: View = layoutInflater.inflate(R.layout.bottom_sheet_cities, null)
        val dialog = BottomSheetDialog(this.requireContext())
        dialog.setContentView(bottomSheetView)
        dialog.setCancelable(false)
        val bottomSheetBehavior: BottomSheetBehavior<*> =
            BottomSheetBehavior.from(bottomSheetView.parent as View)
        val rvListOfDcID: RecyclerView = bottomSheetView.findViewById(R.id.rv_show_list_of_dcid)
        val btnSave = bottomSheetView.findViewById<Button>(R.id.btn_save_data)
        val cityList = Arrays.asList(*resources.getStringArray(R.array.cityList))
        val cityListForSelectionAdapter = CityListForSelectionAdapter(
            this.requireContext(),
            cityList
        )
        rvListOfDcID.layoutManager = LinearLayoutManager(this.requireContext())
        rvListOfDcID.adapter = cityListForSelectionAdapter
        btnSave.setOnClickListener {
            val userPreferences = UserPreferences(this.requireContext())
            var selectedCities = userPreferences.getCities() as MutableList<String>

            if (selectedCities.size > 2 && selectedCities.size < 8) {
                val builder = AlertDialog.Builder(this.requireContext())
                builder.setMessage("Cities selected Successfully")
                builder.setPositiveButton("Ok") { dialogInterface, i ->
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                    dialog.dismiss()
                }
                builder.setCancelable(false)
                builder.show()
            } else {
                val builder = AlertDialog.Builder(this.requireContext())
                builder.setMessage("Select minimum 3 and maximum 7 cities")
                builder.setPositiveButton("Ok") { dialogInterface, i -> }
                builder.setCancelable(false)
                builder.show()
            }
        }


        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        dialog.show()

    }
}