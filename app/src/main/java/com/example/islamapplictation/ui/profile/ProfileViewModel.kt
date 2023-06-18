package com.example.islamapplictation.ui.profile

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.islamapplictation.data.citiesprovider.CitiesProvider
import com.example.islamapplictation.data.pojo.cities.CityTypes
import com.example.islamapplictation.data.pojo.quranvoice.FilterdQuranVoice
import com.example.islamapplictation.data.pojo.quranvoice.QuranVoice
import com.example.islamapplictation.data.remote.quranvoiceservice.QuranFilteredVoiceUseCase
import com.example.islamapplictation.data.remote.quranvoiceservice.QuranVoiceApiService
import com.example.islamapplictation.data.remote.quranvoiceservice.QuranVoiceFilterdStatus
import com.example.islamapplictation.prayersnotifivcation.PrayersPreferences
import com.example.islamapplictation.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.stream.Collectors
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(val quranFilteredVoiceUseCase: QuranFilteredVoiceUseCase) :
    ViewModel() {

    private var _quranVoceMutableStateFlow =
        MutableStateFlow<ArrayList<FilterdQuranVoice>>(ArrayList())
    val quranFilterdStateFlow: StateFlow<ArrayList<FilterdQuranVoice>> = _quranVoceMutableStateFlow
    suspend fun getFilteredQuranVoices() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val useCase = quranFilteredVoiceUseCase.invoke()) {
                is QuranVoiceFilterdStatus.Success -> {
                    _quranVoceMutableStateFlow.value = useCase.resultArrayList
                }

                is QuranVoiceFilterdStatus.Failure -> {
                    _quranVoceMutableStateFlow.value =
                        arrayListOf(FilterdQuranVoice("", useCase.errorText))
                }
            }
        }
    }

    fun getAllCitiesOfThisCountry(contex: Context, country: String): ArrayList<CityTypes> {
        val cities = CitiesProvider.getCityByCountry(contex, country)
        return cities
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()

    }

    fun saveProfileData(
        baseContext: Context?,
        city: String,
        country: String,
        selectedPrayerTimeMethod: String,
        selectedQuranVoice: FilterdQuranVoice,
        selectedImageUri:String
    ) {
        PrayersPreferences(baseContext!!).apply {
            this.city = city
            this.country = country
            this.method = selectedPrayerTimeMethod.toInt()
            this.quranVoiceName = selectedQuranVoice.name
            this.quranVoiceIdentifier = selectedQuranVoice.identifier
            this.profileImageString = selectedImageUri
        }
    }


}


