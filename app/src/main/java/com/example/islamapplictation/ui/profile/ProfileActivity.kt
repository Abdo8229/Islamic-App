package com.example.islamapplictation.ui.profile

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
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
import com.example.islamapplictation.prayersnotifivcation.PrayersPreferences
import com.example.islamapplictation.ui.prayertimes.prayertimeshome.TwoTypesSpinnerStringAdapter
import com.example.islamapplictation.util.CheckPermisions
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {
    private lateinit var selectedImageString: String
    private val prayerTimesMethodsArray: Array<out String> by lazy { resources.getStringArray(R.array.prayerTimeMethods) }
    private val countriesArray: Array<out String> by lazy { resources.getStringArray(R.array.countries) }
    private lateinit var binding: ActivityProfileBinding
    private val SELECT_PICTURE = 200
    private val viewModel: ProfileViewModel by viewModels()
    private val TAG = "ProfileActivity"
    private lateinit var twoTypesSpinnerStringAdapterForQuranVoice: TwoTypesSpinnerStringAdapter<FilterdQuranVoice>
    private lateinit var twoTypesSpinnerStringAdapterForCities: TwoTypesSpinnerStringAdapter<CityTypes>
    private lateinit var quranVoiceList: ArrayList<FilterdQuranVoice>
    private lateinit var citiesArrayList: ArrayList<CityTypes>
    private lateinit var selectedCity: CityTypes
    private lateinit var selectedQuranVoice: FilterdQuranVoice
    private lateinit var selectedPrayerTimeMethod: String
    private lateinit var selectedCountry: String
    private var selectedImageBitmap: Bitmap? = null


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
                selectedQuranVoice = quranVoiceList[position]
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
                selectedCountry = countriesArray[position]
            }


            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.spinnerCity.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                selectedCity = citiesArrayList[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        binding.spinnerPrayerTimeMethod.onItemSelectedListener =
            object : OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                    selectedPrayerTimeMethod = prayerTimesMethodsArray[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
        binding.btnSave.setOnClickListener {
//            Log.d(TAG, "onCreate: ${selectedCity.city + selectedCity.country + selectedPrayerTimeMethod + selectedQuranVoice + selectedImageUri}")
            viewModel.saveProfileData(
                baseContext,
                selectedCity.city,
                selectedCity.country,
                selectedPrayerTimeMethod,
                selectedQuranVoice,
                selectedImageString
            )
            Snackbar.make(binding.root, "Data saved", Snackbar.LENGTH_SHORT).show().apply {

            }
        }

    }

    private fun initData() {
        CheckPermisions(this).checkPermission()
        getQuranVoices()
        initPrayerTimesMethodsSpinnerAdapter()
        initCountrySpinnerAdapter()
        setImageProfile()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        CheckPermisions(this).onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun setImageProfile() {
        val profileImage = findViewById<CircleImageView>(R.id.profile_img_user_profile)
        if (PrayersPreferences(baseContext).profileImageString == null || PrayersPreferences(
                baseContext
            ).profileImageString!!.isEmpty()
        ) {
            profileImage.setImageResource(R.drawable.round_person_24)
        } else {
            val imageBytes = Base64.decode(PrayersPreferences(baseContext).profileImageString, 0)
            val image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            profileImage.setImageBitmap(image)
        }
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

        twoTypesSpinnerStringAdapterForCities = TwoTypesSpinnerStringAdapter(
            citiesArrayList.map {
                it.toString()
            }.toList(),
            baseContext,
            citiesArrayList
        )
        binding.spinnerCity.adapter = twoTypesSpinnerStringAdapterForCities


    }

    private fun initQuranVoiceSpinnerAdapter() {
        twoTypesSpinnerStringAdapterForQuranVoice = TwoTypesSpinnerStringAdapter(
            quranVoiceList.map {
                it.name.toString()
            },
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
                selectedImageBitmap = MediaStore.Images.Media.getBitmap(
                    this.contentResolver,
                    data?.data
                )
                binding.profileImgUserProfile.setImageBitmap(selectedImageBitmap)
                val baos = ByteArrayOutputStream()
                selectedImageBitmap?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val b = baos.toByteArray()
                selectedImageString = Base64.encodeToString(b, Base64.DEFAULT)


            }
        }
    }


}