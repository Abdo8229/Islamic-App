package com.example.islamapplictation.data.pojo.quranvoice

import com.google.gson.annotations.SerializedName

data class QuranVoice(

    @SerializedName("englishName")
    val englishName: String,
    @SerializedName("format")
    val format: String,
    @SerializedName("identifier")
    val identifier: String,
    @SerializedName("language")
    val language: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("type")
    val type: String
)