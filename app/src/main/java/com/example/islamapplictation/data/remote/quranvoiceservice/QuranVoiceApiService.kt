package com.example.islamapplictation.data.remote.quranvoiceservice

import com.example.islamapplictation.data.pojo.quranvoice.QuranVoice
import com.example.islamapplictation.util.Resource
import retrofit2.Response
import retrofit2.http.GET

interface QuranVoiceApiService {
    @GET("islamic-network/cdn/master/info/cdn_surah_audio.json")
    suspend fun getQuranVoices():Response<List<QuranVoice>>
}