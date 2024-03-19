package com.example.islamapplictation.di

import com.example.islamapplictation.data.remote.prayertimes.PrayerTimesApiService
import com.example.islamapplictation.data.remote.quranvoiceservice.QuranVoiceApiService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

const val BASE_URL = "https://api.aladhan.com/"
const val QURAN_VOICES_BASE_URL = "https://raw.githubusercontent.com/"

@Module
@InstallIn(SingletonComponent::class)
object ApiViewModelsComponents  {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .callTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun getInstance( okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
//          .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
            .build()
    }

    @Provides
    @Singleton
    @Named("QuranVoice")
    fun getInstanceToProvidesQuranVoice(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(QURAN_VOICES_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
//          .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
            .build()
    }

    @Provides
    @Singleton
    fun createApi(retrofit: Retrofit): PrayerTimesApiService {
        return retrofit.create(PrayerTimesApiService::class.java)
    }

    @Provides
    @Singleton
    fun createApiToProvidesQuranVoiceApi(
        @Named("QuranVoice")
        retrofit: Retrofit
    ): QuranVoiceApiService {
        return retrofit.create(QuranVoiceApiService::class.java)
    }


}