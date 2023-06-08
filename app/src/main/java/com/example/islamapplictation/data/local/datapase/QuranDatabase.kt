package com.example.islamapplictation.data.local.datapase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.islamapplictation.data.pojo.Aya

@Database(entities = [Aya::class], version = 1)
abstract class QuranDatabase : RoomDatabase() {
    abstract fun quranDao(): QuranDao
}


