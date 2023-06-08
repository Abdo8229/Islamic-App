package com.example.islamapplictation.ui.azkar.azkarlist

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.islamapplictation.data.azkarprovider.AzkarProvider
import com.example.islamapplictation.data.pojo.Zekr
import com.example.islamapplictation.data.pojo.ZekrTypes
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.stream.Collectors
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class AzkarListViewModel @Inject constructor(
    @Named("getAllAzkar")
    val getAllAzkar: ArrayList<Zekr>
) : ViewModel() {

//    fun getAzkarByType(context: Context, zekareType: String): ArrayList<Zekr> {
//        return AzkarProvider().getAzkarByType(context, zekareType)
//    }
//
//    fun getAzkarTypes(context: Context): HashSet<ZekrTypes> {
//
//        return AzkarProvider().getAzkarTypes(context)
//    }

    //    private val TAG = "AzkarProvider"
//    {
//        "category": "أذكار الصباح",
//        "count": "1",
//        "description": "من قالها حين يصبح أجير من الجن حتى يمسى ومن قالها حين يمسى أجير من الجن حتى يصبح.",
//        "reference": "[آية الكرسى - البقرة 255].",
//        "zekr": "أَعُوذُ بِاللهِ مِنْ الشَّيْطَانِ الرَّجِيمِ\nاللّهُ لاَ إِلَـهَ إِلاَّ هُوَ الْحَيُّ الْقَيُّومُ لاَ تَأْخُذُهُ سِنَةٌ وَلاَ نَوْمٌ لَّهُ مَا فِي السَّمَاوَاتِ وَمَا فِي الأَرْضِ مَن ذَا الَّذِي يَشْفَعُ عِنْدَهُ إِلاَّ بِإِذْنِهِ يَعْلَمُ مَا بَيْنَ أَيْدِيهِمْ وَمَا خَلْفَهُمْ وَلاَ يُحِيطُونَ بِشَيْءٍ مِّنْ عِلْمِهِ إِلاَّ بِمَا شَاء وَسِعَ كُرْسِيُّهُ السَّمَاوَاتِ وَالأَرْضَ وَلاَ يَؤُودُهُ حِفْظُهُمَا وَهُوَ الْعَلِيُّ الْعَظِيمُ.\n[آية الكرسى - البقرة 255]."
//    },
    fun getAzkar(azkarType: String): ArrayList<Zekr> {
//    Log.d(TAG, "getAzkar: $azkarType")
//        for (i in 1 .. 3){
//            Log.d(TAG, "getAzkar: ${AzkarProvider().getAzkarByType(context,azkarType).get(i)}")
//        }
        return getAllAzkar
            .stream()
            .filter { zekr -> azkarType.equals(zekr.category) }
            .collect(Collectors.toCollection { ArrayList() })
    }
}