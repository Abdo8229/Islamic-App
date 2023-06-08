package com.example.islamapplictation.data.remote.prayertimes

import com.example.islamapplictation.data.pojo.prayertimes.PrayerTimesResponce
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PrayerTimesApiService {

    //    http://api.aladhan.com/v1/calendarByCity/2023/5?city=Alexandria&country=Egypt&method=2
    @GET("v1/calendarByCity/{year}/{month}")
   suspend  fun getAllPrayerTimes(
        @Path("year") year: Int,
        @Path("month") month: Int,
        @Query("city") city: String,
        @Query("country")country:String,
        @Query("method")method:Int
    ): Response<PrayerTimesResponce>

    @GET("v1/calendarByCity/{year}/{month}")
      fun getAllPrayerTimesForAzan(
        @Path("year") year: Int,
        @Path("month") month: Int,
        @Query("city") city: String,
        @Query("country")country:String,
        @Query("method")method:Int
    ): Call<PrayerTimesResponce>
}