package com.example.islamapplictation.ui.quran.search

import androidx.lifecycle.ViewModel
import com.example.islamapplictation.data.pojo.Aya
import com.example.islamapplictation.data.local.datapase.repo.SoraListRepoImp
import com.example.islamapplictation.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuranSearchViewModel @Inject constructor(
    private val soraListRepoImp: SoraListRepoImp,
   ): ViewModel() {

    suspend fun getSearchResults(keyWord: String): List<Aya> {

       val quranSearchData = soraListRepoImp.getAyaBySubText(keyWord)
        when(quranSearchData){
            is Resource.Success->{
                return quranSearchData.data!!
            }
            is Resource.Error->{
                return emptyList()
            }
        }

    }

}