package com.example.islamapplictation

import android.app.Application
import android.content.Context
import androidx.work.Configuration
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.islamapplictation.data.remote.prayertimes.PrayerTimesApiService
import com.example.islamapplictation.data.remote.prayertimes.prayertimerepo.PrayerTimeRepoImp
import com.example.islamapplictation.prayersnotifivcation.RegisterPrayerTimeWorker
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class IslamApplication : Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: RegisterPrayerTimeWorkerFactory
    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .setWorkerFactory(workerFactory)
            .build()
    }

}

class RegisterPrayerTimeWorkerFactory @Inject constructor(
    private val apiRepoImp: PrayerTimeRepoImp) :
    WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? = when (workerClassName) {
        RegisterPrayerTimeWorker::class.java.name ->
            RegisterPrayerTimeWorker(apiRepoImp, appContext, workerParameters)

        else ->
            null
    }
}