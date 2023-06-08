package com.example.islamapplictation.data.remote.prayertimes.prayertimerepo

import com.example.islamapplictation.data.pojo.prayertimes.PrayerTimesResponce
import com.example.islamapplictation.util.Resource
import dagger.Component
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Path
import retrofit2.http.Query
interface PrayerTimeRepo{
    suspend fun getPrayerTimes(
        year: Int,
        month: Int,
        city: String,
        country: String,
        method: Int
    ): Resource<PrayerTimesResponce>

}