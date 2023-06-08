package com.example.islamapplictation.prayersnotifivcation

import android.content.Context
import android.content.SharedPreferences

class PrayersPreferences(
    context: Context
//    ,
//    private val _city: String?,
//    private val _country: String?,
//    private val _method: Int?
) {

    private val FILE_NAME = "PRAYERS_PREF"
    private val CITY_KEY = "CItY_KEY"
    private val COUNTRY_KEY = "COUNTRY_KEY"
    private val METHOD_KEY = "METHOD_KEY"
    private val sharedPreferences: SharedPreferences


    init {
        sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)

    }

    var city: String?
       get() =  sharedPreferences.getString(CITY_KEY, "Cairo")
        set(value) = sharedPreferences.edit().putString(CITY_KEY,value).apply()
    var country: String? = sharedPreferences.getString(COUNTRY_KEY, "Egypt")
        set(value) = sharedPreferences.edit().putString(COUNTRY_KEY, value).apply()
    var method: Int? = sharedPreferences.getInt(METHOD_KEY, 1)
        set(value) = sharedPreferences.edit().putInt(METHOD_KEY, value ?: 1).apply()

}