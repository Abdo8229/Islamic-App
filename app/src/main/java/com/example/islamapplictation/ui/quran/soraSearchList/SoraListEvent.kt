package com.example.islamapplictation.ui.quran.soraSearchList

import com.example.islamapplictation.data.pojo.Sora

sealed class SoraListEvent {
    object GetAllSoraEvent : SoraListEvent()

}