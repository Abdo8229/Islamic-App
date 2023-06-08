package com.example.islamapplictation.data.local.datapase.repo


import com.example.islamapplictation.data.pojo.Aya
import com.example.islamapplictation.data.pojo.Jozz
import com.example.islamapplictation.data.pojo.Sora
import com.example.islamapplictation.util.Resource

interface SoraListRepo {
suspend fun getAllSora():Resource<ArrayList<Sora>>
suspend  fun getAyaBySubText(keyWord: String):Resource< List<Aya>>

//    suspend fun getAyaByPage(page :Int):List<Aya>
//    suspend fun getSoraByNumber(soraNumber:Int): Sora

   }