package com.example.islamapplictation.di

import android.content.Context
import com.example.islamapplictation.data.pojo.Zekr
import com.example.islamapplictation.data.pojo.ZekrTypes
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okio.IOException
import java.io.InputStream
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AzkarModule {

    @Provides
    @Singleton
    @Named("getAllAzkar")
    fun getAllAzkar(@ApplicationContext context: Context): ArrayList<Zekr> {
        return try {
            val azkarFile: InputStream = context.assets.open("azkar/azkar.json")
            val size: Int = azkarFile.available()
            val bytes = ByteArray(size)
            azkarFile.read(bytes)
            azkarFile.close()
            val azkarString = String(bytes, Charsets.UTF_8)
            val gson = Gson()
            val itemType = object : TypeToken<ArrayList<Zekr>>() {}.type
            gson.fromJson(azkarString, itemType)
        } catch (e: IOException) {
            ArrayList()
        }
    }

    //    @Provides
//    @Singleton
//    @Named("proviedsAzkarByType")
//    fun proviedsAzkarByType( zekareType: String,
//                       @Named("getAllAzkar") getAllAzkar : ArrayList<Zekr>
//    ): ArrayList<Zekr> {
//        return getAllAzkar
//            .stream()
//            .filter { zekr -> zekareType.equals(zekr.category) }
//            .collect(Collectors.toCollection { ArrayList() })
//
//    }
    @Provides
    @Singleton
//    @Named("providesAzkarTypes")
    fun proviedsAzkarTypes( @Named("getAllAzkar")
                           getAllAzkar: ArrayList<Zekr>): HashSet<ZekrTypes> {
        return getAllAzkar.map { zekr -> ZekrTypes(zekr.category) }.toHashSet()
    }


}