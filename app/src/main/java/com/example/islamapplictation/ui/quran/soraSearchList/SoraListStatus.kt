package com.example.islamapplictation.ui.quran.soraSearchList

import com.example.islamapplictation.data.pojo.Sora

sealed class SoraListStatus {
    class Success(val resultArrayList: ArrayList<Sora>) : SoraListStatus()
    class Failure(val errorText: String) : SoraListStatus()
    class Loading : SoraListStatus()
    object Empty : SoraListStatus()
}