package com.example.islamapplictation.data.pojo.quranvoice

data class FilterdQuranVoice(
    val identifier: String,
    val name: String
){
    override fun toString(): String {
        return name
    }
}