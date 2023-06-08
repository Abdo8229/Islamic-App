package com.example.islamapplictation.data.azkarprovider

import android.content.Context
import com.example.islamapplictation.data.pojo.Zekr
import com.example.islamapplictation.data.pojo.ZekrTypes
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import okio.IOException
import org.jetbrains.annotations.NotNull
import java.io.InputStream
import java.util.stream.Collectors
import javax.inject.Inject

class AzkarProvider {


//    private fun getAllAzkar(context: Context): ArrayList<Zekr> {
//        return try {
//            val azkarFile: InputStream = context.assets.open("azkar/azkar.json")
//            val size: Int = azkarFile.available()
//            val bytes = ByteArray(size)
//            azkarFile.read(bytes)
//            azkarFile.close()
//            val azkarString = String(bytes, Charsets.UTF_8)
//            val gson = Gson()
//            val itemType = object : TypeToken<ArrayList<Zekr>>() {}.type
//            gson.fromJson(azkarString, itemType)
//        } catch (e: IOException) {
//            ArrayList()
//        }
//
//
//    }
//
//    fun getAzkarByType(context: Context, zekareType: String): ArrayList<Zekr> {
//        return getAllAzkar(context)
//            .stream()
//            .filter { zekr -> zekareType.equals(zekr.category) }
//            .collect(Collectors.toCollection { ArrayList() })
//
//    }
//
//    fun getAzkarTypes(context: Context): HashSet<ZekrTypes> {
//        return getAllAzkar(context)
//            .stream()
//            .map { zekr -> ZekrTypes(zekr.category) }
//            .collect(Collectors.toCollection { HashSet() })
//
//    }


}