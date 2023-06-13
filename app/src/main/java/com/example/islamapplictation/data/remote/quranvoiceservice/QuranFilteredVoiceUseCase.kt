package com.example.islamapplictation.data.remote.quranvoiceservice

import com.example.islamapplictation.data.pojo.quranvoice.FilterdQuranVoice
import com.example.islamapplictation.data.pojo.quranvoice.QuranVoice
import java.util.stream.Collectors
import javax.inject.Inject

class QuranFilteredVoiceUseCase @Inject constructor (val quranVoiceApiRepoImp: QuranVoiceApiRepoImp) {
    suspend fun invoke () : QuranVoiceFilterdStatus{
      return  when (val  responce = quranVoiceApiRepoImp.getAllQuranVoices())
        {
            is QuranVoiceStatus.Success->{
                QuranVoiceFilterdStatus.Success(filterList(responce.resultArrayList))
            }
            is QuranVoiceStatus.Failure->{
                QuranVoiceFilterdStatus.Failure(responce.errorText)
            }
        }

    }
    private fun filterList(body: List<QuranVoice>): ArrayList<FilterdQuranVoice> {
        return body.stream()
            .map { quranVoice -> FilterdQuranVoice(quranVoice.identifier, quranVoice.name) }
            .collect(Collectors.toCollection { ArrayList() })


    }

}
sealed class QuranVoiceFilterdStatus {
    class Success(val resultArrayList: ArrayList<FilterdQuranVoice>) : QuranVoiceFilterdStatus()
    class Failure(val errorText: String) : QuranVoiceFilterdStatus()

}