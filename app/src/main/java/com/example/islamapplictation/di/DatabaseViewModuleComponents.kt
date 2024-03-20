package com.example.islamapplictation.di

import android.content.Context
import androidx.room.Room
import com.example.islamapplictation.data.local.datapase.QuranDao
import com.example.islamapplictation.data.local.datapase.QuranDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

const val DATABASE_NAME = "quran.db"
@Module
@InstallIn(SingletonComponent::class)
object DatabaseViewModuleComponents {
    //    Database for Quran Search
    @Provides
    @Singleton
    fun getInstance (quranDatabase: QuranDatabase): QuranDao {
        return quranDatabase.quranDao()
    }

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): QuranDatabase {
        return Room.databaseBuilder(
            context,
            QuranDatabase::class.java,
            DATABASE_NAME
        )
            .createFromAsset("quran.db")
            .build()
    }



}