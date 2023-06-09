package com.example.islamapplictation.di

import com.example.islamapplictation.data.remote.prayertimes.PrayerTimesApiService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val BASE_URL = "https://api.aladhan.com/"


@Module
@InstallIn(ViewModelComponent::class)
object ApiViewModelsComponents{
//    Api Service for Prayer Times Api
    @Provides
    fun providesBaseUrl() = BASE_URL

    @Provides
    @ViewModelScoped
    fun provideOkHttpClient():OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
       return OkHttpClient.Builder()
            .connectTimeout(20,TimeUnit.SECONDS)
           .callTimeout(20,TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    }
    @Provides
    @ViewModelScoped
     fun getInstance(BASE_URL: String,okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
//          .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
            .build()
    }
    @Provides
    @ViewModelScoped
    fun createApi(retrofit:Retrofit): PrayerTimesApiService {
        return retrofit.create(PrayerTimesApiService::class.java)
    }



}