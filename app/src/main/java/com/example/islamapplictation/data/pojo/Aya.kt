package com.example.islamapplictation.data.pojo

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quran")
data class Aya(
    @PrimaryKey
     val id: Int,
     val jozz: Int,
     val sora: Int,
    @NonNull
     val sora_name_en: String,

    @NonNull
     val sora_name_ar: String,

     val page :Int,
     val line_start:Int,
     val line_end:Int,
     val aya_no:Int,

    @NonNull
     val aya_text:String,

    @NonNull
     val aya_text_emlaey:String
)