package com.example.islamapplictation.data.remote.quranvoiceservice

import android.annotation.SuppressLint
import com.example.islamapplictation.data.pojo.quranvoice.QuranVoice
import com.example.islamapplictation.util.Resource
import javax.inject.Inject

class QuranVoiceApiRepoImp @Inject constructor(val quranVoiceApiService: QuranVoiceApiService) {
   suspend  fun getAllQuranVoices():QuranVoiceStatus{
       val responce =quranVoiceApiService.getQuranVoices()
         if(responce.isSuccessful){

         return QuranVoiceStatus.Success(responce.body() as ArrayList<QuranVoice>)
       }else{
           return QuranVoiceStatus.Failure(responce.errorBody().toString())
       }

    }
}
sealed class QuranVoiceStatus {
    class Success(val resultArrayList: ArrayList<QuranVoice>) : QuranVoiceStatus()
    class Failure(val errorText: String) : QuranVoiceStatus()

}