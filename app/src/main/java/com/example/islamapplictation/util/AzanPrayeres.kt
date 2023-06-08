package com.example.islamapplictation.util

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.islamapplictation.prayersnotifivcation.RegisterPrayerTimeWorker
import java.util.concurrent.TimeUnit

class AzanPrayeres {
    companion object{
        fun  registerPrayers(context:Context){
            val registerRequest = PeriodicWorkRequest
                .Builder(RegisterPrayerTimeWorker::class.java,30, TimeUnit.DAYS)
                .addTag("REGISTER_PRAYERS")
                .build()
            WorkManager.getInstance(context.applicationContext)
                .enqueueUniquePeriodicWork("REGISTER_PRAYERS",
                    ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,registerRequest)

        }
    }
}