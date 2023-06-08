package com.example.islamapplictation.data.remote.prayertimes

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiServiceCreation {
    private val BASE_URL = "http://api.aladhan.com/"
    private fun retrofitInstance(): Retrofit {
        return Retrofit .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
    fun getPrayerTimeApi(): PrayerTimesApiService {
        return retrofitInstance().create(PrayerTimesApiService::class.java)
    }
}