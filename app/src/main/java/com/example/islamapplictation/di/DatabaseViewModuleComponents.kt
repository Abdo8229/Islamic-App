package com.example.islamapplictation.di

import android.content.Context
import androidx.room.Room
import com.example.islamapplictation.data.azkarprovider.AzkarProvider
import com.example.islamapplictation.data.local.datapase.QuranDao
import com.example.islamapplictation.data.local.datapase.QuranDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
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
    fun proviedsDatabase(@ApplicationContext context: Context): QuranDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            QuranDatabase::class.java,
            DATABASE_NAME
        )
            .createFromAsset("quran.db")
            .build()
    }



}