package com.example.islamapplictation.ui.quran.quranpage

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.islamapplictation.data.quranprovider.PagesManger

class QuranViewModel : ViewModel(){


    fun getQuranImagesPageByNumber(context: Context, pageNumber: Int): Int {
        return  PagesManger().getQuranImagesPageByNumber(context,pageNumber)
    }
}