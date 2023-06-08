package com.example.islamapplictation.data.pojo.prayertimes

data class Date(
    val gregorian: Gregorian,
    val hijri: Hijri,
    val readable: String,
    val timestamp: String
)