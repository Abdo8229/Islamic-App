package com.example.islamapplictation.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.islamapplictation.R
import com.example.islamapplictation.data.pojo.cities.CityTypes
import com.example.islamapplictation.data.pojo.quranvoice.FilterdQuranVoice
import com.example.islamapplictation.databinding.ActivityProfileBinding
import com.example.islamapplictation.ui.prayertimes.prayertimeshome.TwoStingSTypeArrayList
import com.example.islamapplictation.ui.prayertimes.prayertimeshome.TwoTypesSpinnerStringAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {
    private val prayerTimesMethodsArray: Array<out String> by lazy { resources.getStringArray(R.array.prayerTimeMethods) }
    private val countriesArray: Array<out String> by lazy { resources.getStringArray(R.array.countries) }
    private lateinit var binding: ActivityProfileBinding
    private val SELECT_PICTURE = 200
    private lateinit var selectedImageUri: Uri
    private val viewModel: ProfileViewModel by viewModels()
    private val TAG = "ProfileActivity"
    private lateinit var twoTypesSpinnerStringAdapterForQuranVoice: TwoTypesSpinnerStringAdapter<FilterdQuranVoice>
    private lateinit var twoTypesSpinnerStringAdapterForCities: TwoTypesSpinnerStringAdapter<CityTypes>
    private lateinit var quranVoiceList: ArrayList<FilterdQuranVoice>
    private lateinit var citiesArrayList: ArrayList<CityTypes>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initData()

        binding.spinnerVoice.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Log.d(TAG, "onItemSelected: ${quranVoiceList[position]}")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d(TAG, "onNothingSelected: ")
            }
        }
        binding.btnChangeProfilePicture.setOnClickListener {
            imageChooser()
        }
        binding.knowMoreAboutPrayerTimeMethod.setOnClickListener {
            goToAladanWebSite()
        }
        binding.spinnerCountry.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                citiesArrayList =
                    viewModel.getAllCitiesOfThisCountry(
                        baseContext,
                        countriesArray[position].lowercase()
                    )
                setCitiesAdapter()
            }


            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

    }

    private fun initData() {
        getQuranVoices()
        initPrayerTimesMethodsSpinnerAdapter()
        initCountrySpinnerAdapter()
    }

    private fun initPrayerTimesMethodsSpinnerAdapter() {
        val adapter = ArrayAdapter(
            baseContext,
            android.R.layout.simple_list_item_activated_1,
            prayerTimesMethodsArray
        )
        binding.spinnerPrayerTimeMethod.adapter = adapter
    }
    private fun initCountrySpinnerAdapter() {
        val adapter = ArrayAdapter(
            baseContext,
            android.R.layout.simple_list_item_activated_1,
            countriesArray
        )
        binding.spinnerCountry.adapter = adapter
    }
    private fun setCitiesAdapter() {

        twoTypesSpinnerStringAdapterForCities = TwoTypesSpinnerStringAdapter(TwoStingSTypeArrayList.Cites(citiesArrayList),baseContext, citiesArrayList)
        binding.spinnerCity.adapter = twoTypesSpinnerStringAdapterForCities


    }

    private fun initQuranVoiceSpinnerAdapter() {
        twoTypesSpinnerStringAdapterForQuranVoice = TwoTypesSpinnerStringAdapter(
            TwoStingSTypeArrayList.QuranVoices(quranVoiceList),
            baseContext,
            quranVoiceList
        )
        binding.spinnerVoice.adapter = twoTypesSpinnerStringAdapterForQuranVoice
    }

    private fun getQuranVoices() {
        lifecycleScope.launch {
            viewModel.getFilteredQuranVoices()
            viewModel.quranFilterdStateFlow.collect {
                quranVoiceList = it
                initQuranVoiceSpinnerAdapter()
            }
        }
    }


    private fun goToAladanWebSite() {
        val url = "https://aladhan.com/calculation-methods"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    private fun imageChooser() {

        // create an instance of the
        // intent of the type image
        val i = Intent()
        i.type = "image/*"
        i.action = Intent.ACTION_GET_CONTENT

        // pass the constant to compare it
        // with the returned requestCode

        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE)

    }


    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                selectedImageUri = data?.data!!
                binding.imgUserProfile.setImageURI(selectedImageUri)
            }
        }
    }


}