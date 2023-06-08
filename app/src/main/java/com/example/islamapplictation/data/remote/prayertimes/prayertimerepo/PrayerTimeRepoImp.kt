package com.example.islamapplictation.data.remote.prayertimes.prayertimerepo

import com.example.islamapplictation.data.pojo.prayertimes.PrayerTimesResponce
import com.example.islamapplictation.data.remote.prayertimes.PrayerTimesApiService
import com.example.islamapplictation.util.Resource
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class PrayerTimeRepoImp @Inject constructor(val prayerTimesApiService: PrayerTimesApiService) : PrayerTimeRepo {

    override suspend fun getPrayerTimes(
        year: Int,
        month: Int,
        city: String,
        country: String,
        method: Int
    ): Resource<PrayerTimesResponce> {
//        val instance = PrayerTimesApiCreation().createApi()
//        val api = instance.getAllPrayerTimes(year, month, city, country, method)
       val api =  prayerTimesApiService.getAllPrayerTimes(year, month, city, country, method)
        val response = api.body()
        if (response != null) {
            return Resource.Success(response)
        } else {
            return Resource.Error("An Error occur ${api.errorBody()} & Error Code = ${api.body()!!.code}")
        }
    }

}