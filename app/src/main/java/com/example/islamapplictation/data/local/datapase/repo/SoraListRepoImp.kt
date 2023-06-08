package com.example.islamapplictation.data.local.datapase.repo

import android.content.Context
import com.example.islamapplictation.data.local.datapase.QuranDao
import com.example.islamapplictation.data.local.datapase.QuranDatabase
import com.example.islamapplictation.data.pojo.Aya
import com.example.islamapplictation.data.pojo.Sora
import com.example.islamapplictation.util.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SoraListRepoImp @Inject constructor(
    private val quanDao: QuranDao,

)
    : SoraListRepo {
    override suspend fun getAllSora(): Resource<ArrayList<Sora>> {

        val soraArrayList: ArrayList<Sora> = ArrayList<Sora>()
        soraArrayList.ensureCapacity(114)
        return try {
            val quranDatabase = quanDao
            for (i in 0..113) {
                soraArrayList.add(i, quranDatabase.getSoraByNumber(i + 1))
            }

            if (soraArrayList.size == 114) {
                Resource.Success(soraArrayList)
            } else {
                Resource.Error("Cant get Data")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An Error occur")
        }
    }

    override suspend fun getAyaBySubText(keyWord: String): Resource<List<Aya>> {
//        return quanDao.getAyaBySubText(keyWord)
        return try {
            val quranDatabase = quanDao
            val ayaList = quranDatabase.getAyaBySubText(keyWord)
            if (ayaList.isNotEmpty()) {
                Resource.Success(ayaList)
            } else {
                Resource.Error("Cant get Data")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An Error occur")
        }
    }
}