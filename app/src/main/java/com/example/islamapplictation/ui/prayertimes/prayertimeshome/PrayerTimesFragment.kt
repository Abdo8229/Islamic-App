package com.example.islamapplictation.ui.prayertimes.prayertimeshome

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.islamapplictation.data.pojo.cities.CityTypes
import com.example.islamapplictation.data.pojo.prayertimes.PrayerTiming
import com.example.islamapplictation.databinding.FragmentPrayerTimesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Calendar
@AndroidEntryPoint
class PrayerTimesFragment : Fragment() {
    private val TAG = "PrayerTimesFragment"
    private lateinit var binding: FragmentPrayerTimesBinding
    private val viewModel: PrayerTimesViewModel by viewModels()
    //    by lazy { ViewModelProvider(this)[PrayerTimesViewModel::class.java] }
    private val adapter: PrayerTimesAdapter by lazy { PrayerTimesAdapter(this.requireContext()) }
    private val calendar: Calendar by lazy { Calendar.getInstance() }
    private lateinit var citiesAdapter: CitiesAdapter
    private lateinit var countriesList: List<String>
    private lateinit var citiesArrayList: ArrayList<CityTypes>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        viewModel = ViewModelProvider(this)[PrayerTimesViewModel::class.java]
        countriesList = viewModel.getAllCountries(this.requireContext())
        viewModel.getPrayerTimes(
            this.requireContext(),
            calendar[Calendar.YEAR],
            calendar[Calendar.MONTH],
            calendar[Calendar.DAY_OF_MONTH],
            "Alexandria",
            "Egypt",
            1
        )

        binding = FragmentPrayerTimesBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setCountriesAdapter()
        binding.spCountry.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
             if(position!=0)   {
                    citiesArrayList =
                        viewModel.getAllCitiesOfThisCountry(
                            requireContext(),
                            countriesList[position]
                        )
                    setCitiesAdapter()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d(TAG, "onNothingSelected: ")
            }
        }
        binding.spCity.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
              if(position!=0)  {
                    viewModel.getPrayerTimes(
                        requireContext(),
                        calendar[Calendar.YEAR],
                        calendar[Calendar.MONTH],
                        calendar[Calendar.DAY_OF_MONTH],
                        citiesArrayList[position].city,
                        citiesArrayList[position].country,
                        1
                    )
                    getTimingsData()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d(TAG, "onNothingSelected: ")
            }
        }

        binding.datePickerActions.init(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ) { view, year, monthOfYear, dayOfMonth ->
            viewModel.getPrayerTimes(
                this.requireContext(),
                year,
                monthOfYear,
                dayOfMonth,
                "Alexandria",
                "Egypt",
                1
            )
            getTimingsData()
        }


        setPrayerTimeAdapter()

    }

    private fun setCountriesAdapter() {
        val countryList = viewModel.getAllCountries(this.requireContext())
        val arrayAdapter =
            ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, countryList)
        binding.spCountry.adapter = arrayAdapter
    }

    private fun setCitiesAdapter() {

        citiesAdapter = CitiesAdapter(this.requireContext(), citiesArrayList)
        binding.spCity.adapter = citiesAdapter


    }

    private fun setPrayerTimeAdapter() {
        binding.rvPrayerTimes.addItemDecoration(
            DividerItemDecoration(
                this.requireContext(),
                GridLayoutManager.VERTICAL
            )
        )
        binding.rvPrayerTimes.adapter = adapter
        getTimingsData()
        //        lifecycleData()
    }

    private fun getTimingsData() {
        lifecycleScope.launch {
            viewModel.conversionTimings.collect { event ->
                when (event) {
                    is PrayerTimesViewModel.TimingEvent.Success -> {

                        adapter.setDataList(event.timmigsArrayList)
                    }

                    is PrayerTimesViewModel.TimingEvent.Failure -> {
                        adapter.setDataList(arrayListOf(PrayerTiming(event.errorText, "")))
                    }

                    is PrayerTimesViewModel.TimingEvent.Loading -> {
                        Log.d(TAG, "getTimingsData: Is Loading ")
                    }

                    else -> Unit
                }

            }
        }
    }

    /** private fun lifecycleData() {
    lifecycleScope.launchWhenStarted {
    viewModel.conversion.collect { event ->
    when (event) {
    is PrayerTimesViewModel.PrayerTimesEvent.Success -> {
    }

    is PrayerTimesViewModel.PrayerTimesEvent.Failure -> {
    }

    is PrayerTimesViewModel.PrayerTimesEvent.Loading -> {

    Log.d(TAG, "lifecycleData: Loading")
    }

    else -> Unit


    }


    }
    }
    }
     **/
}