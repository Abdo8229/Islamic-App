package com.example.islamapplictation.data.local.datapase

import androidx.room.Dao
import androidx.room.Query
import com.example.islamapplictation.data.pojo.Aya
import com.example.islamapplictation.data.pojo.Jozz
import com.example.islamapplictation.data.pojo.Sora


@Dao
interface QuranDao {
    @Query("Select * From quran WHERE page = :page")
   suspend fun getAyaByPage(page :Int):List<Aya>
    @Query("select sora as soraNumber , MIN(page) as startPage , MAX(page) as endPage  ,sora_name_ar as arabicName ,sora_name_en as englishName from quran where sora = :soraNumber ")
   suspend fun getSoraByNumber(soraNumber:Int): Sora

    @Query("SELECT jozz as jozzNumber, MIN(page) as startPage ,MAX(page) as endPage FROM quran WHERE jozz = :jozzNumber")
    fun getJozzByNumber(jozzNumber: Int): Jozz
    @Query("select * from quran where aya_text_emlaey LIKE '%' || :keyWord || '%' ")
    suspend  fun getAyaBySubText(keyWord: String): List<Aya>
}