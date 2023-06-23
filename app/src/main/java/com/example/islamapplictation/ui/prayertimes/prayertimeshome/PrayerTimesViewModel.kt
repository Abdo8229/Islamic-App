package com.example.islamapplictation.ui.prayertimes.prayertimeshome

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.util.TimingLogger
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.islamapplictation.data.citiesprovider.CitiesProvider
import com.example.islamapplictation.data.pojo.cities.City
import com.example.islamapplictation.data.pojo.cities.CityTypes
import com.example.islamapplictation.data.pojo.prayertimes.PrayerTimesResponce
import com.example.islamapplictation.data.pojo.prayertimes.PrayerTiming
import com.example.islamapplictation.data.pojo.prayertimes.Timings
import com.example.islamapplictation.data.remote.prayertimes.prayertimerepo.PrayerTimeRepoImp
import com.example.islamapplictation.prayersnotifivcation.PrayersPreferences
import com.example.islamapplictation.util.AzanPrayeres
import com.example.islamapplictation.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.jetbrains.annotations.NotNull
import java.lang.ref.WeakReference
import javax.inject.Inject

@HiltViewModel
class PrayerTimesViewModel @Inject constructor (private val prayerTimeRepoImp: PrayerTimeRepoImp): ViewModel() {
    private val TAG = "PrayerTimesViewModel"
//    private val pratyerTimesRepoImp: PrayerTimeRepoImp by lazy {
//        PrayerTimeRepoImp()
//    }

    sealed class PrayerTimesEvent {
        class Success(val PrayerTimesResponse: PrayerTimesResponce) : PrayerTimesEvent()
        class Failure(val errorText: String) : PrayerTimesEvent()
        class Loading : PrayerTimesEvent()
        object Empty : PrayerTimesEvent()
    }

    private val _conversion = MutableStateFlow<PrayerTimesEvent>(PrayerTimesEvent.Empty)
    val conversion: StateFlow<PrayerTimesEvent> = _conversion
    private fun setSharedPreferences(
        context: Context,
        city: String,
        country: String,
        method: Int
    ) {
        PrayersPreferences(context).apply {
            this.city = city
            this.country = country
            this.method = method
        }
        AzanPrayeres.registerPrayers(context)
    }

    fun getPrayerTimes(
        context: Context,
        year: Int,
        month: Int,
        day: Int,
        city: String,
        country: String,
        method: Int

    ) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val prayerTimesResponce = prayerTimeRepoImp
                    .getPrayerTimes(year, month+1, city, country, method)) {
                is Resource.Error -> _conversion.value =
                    PrayerTimesEvent.Failure(prayerTimesResponce.massage!!)

                is Resource.Success ->
                    if (prayerTimesResponce.data == null) {
                        PrayerTimesEvent.Failure("Un expected error")
                    } else {
                        _conversion.value = PrayerTimesEvent.Success(prayerTimesResponce.data)
                        if (day >= 1 || day <= 31) {
                            convertFromTimings(prayerTimesResponce.data.data[day - 1].timings)
                            setSharedPreferences(context,city,country,method)
                        } else {
                            _conversion.value =
                                PrayerTimesEvent.Failure("day must be a number from 1 to 31")
                        }
                    }

            }
        }

    }

    sealed class TimingEvent {
        class Success(val timmigsArrayList: ArrayList<PrayerTiming>) : TimingEvent()
        class Failure(val errorText: String) : TimingEvent()
        class Loading : TimingEvent()
        object Empty : TimingEvent()
    }

    private val _conversionTimigs = MutableStateFlow<TimingEvent>(TimingEvent.Empty)
    val conversionTimings: StateFlow<TimingEvent> = _conversionTimigs


    private fun convertFromTimings(timings: Timings) {
        val res: ArrayList<PrayerTiming> = arrayListOf()

        res.add(PrayerTiming("Fajr", timings.Fajr))
        res.add(PrayerTiming("Dhuhr", timings.Dhuhr))
        res.add(PrayerTiming("Asr", timings.Asr))
        res.add(PrayerTiming("Maghrib", timings.Maghrib))
        res.add(PrayerTiming("Isha", timings.Isha))

        _conversionTimigs.value = TimingEvent.Success(res)
    }

    fun getAllCitiesOfThisCountry(contex: Context, country: String): ArrayList<CityTypes> {
        val cities = CitiesProvider.getCityByCountry(contex, country)
        return cities
    }

}