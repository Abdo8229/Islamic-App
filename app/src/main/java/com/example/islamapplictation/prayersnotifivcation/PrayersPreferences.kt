package com.example.islamapplictation.prayersnotifivcation

import android.content.Context
import android.content.SharedPreferences

class PrayersPreferences(
    context: Context
) {

    private val FILE_NAME = "PRAYERS_PREF"
    private val CITY_KEY = "CItY_KEY"
    private val COUNTRY_KEY = "COUNTRY_KEY"
    private val METHOD_KEY = "METHOD_KEY"
    private val QURAN_VOICE_IDENTIFIER_KEY = "QURAN_VOICE_IDENTIFIER_KEY"
    private val QURAN_VOICE_NAME_KEY = "QURAN_VOICE_NAME_KEY"
    private val PROFILE_URI_KEY = "PROFILE_IMAGE_BITMAP_KE"
    private val sharedPreferences: SharedPreferences


    init {
        sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)

    }

    var city: String?
       get() =  sharedPreferences.getString(CITY_KEY, "Alexandria")
        set(value) = sharedPreferences.edit().putString(CITY_KEY,value).apply()
    var country: String? = sharedPreferences.getString(COUNTRY_KEY, "Egypt")
        set(value) = sharedPreferences.edit().putString(COUNTRY_KEY, value).apply()
    var method: Int? = sharedPreferences.getInt(METHOD_KEY, 1)
        set(value) = sharedPreferences.edit().putInt(METHOD_KEY, value ?: 1).apply()
    var quranVoiceIdentifier: String? = sharedPreferences.getString(QURAN_VOICE_IDENTIFIER_KEY, "ar.alafasy")
        set(value) = sharedPreferences.edit().putString(QURAN_VOICE_IDENTIFIER_KEY, value).apply()
    var quranVoiceName: String? = sharedPreferences.getString(QURAN_VOICE_NAME_KEY, "Mishary Rashid Alafasy")
        set(value) = sharedPreferences.edit().putString(QURAN_VOICE_NAME_KEY, value).apply()
    var profileImageString: String? = sharedPreferences.getString(PROFILE_URI_KEY, "")
        set(value) = sharedPreferences.edit().putString(PROFILE_URI_KEY, value).apply()

}