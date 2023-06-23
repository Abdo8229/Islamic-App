package com.example.islamapplictation.prayersnotifivcation

import android.annotation.SuppressLint
import android.content.Context
import android.icu.util.Calendar
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import  com.example.islamapplictation.data.pojo.prayertimes.Data as Data1
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.islamapplictation.data.pojo.azan.AzanNotificationConstant
import com.example.islamapplictation.data.pojo.prayertimes.PrayerTimesResponce
import com.example.islamapplictation.data.pojo.prayertimes.PrayerTiming
import com.example.islamapplictation.data.pojo.prayertimes.Timings
import com.example.islamapplictation.data.remote.prayertimes.ApiServiceCreation
import com.example.islamapplictation.data.remote.prayertimes.PrayerTimesApiService
import com.example.islamapplictation.data.remote.prayertimes.prayertimerepo.PrayerTimeRepoImp
import com.example.islamapplictation.util.Resource
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.math.abs

@HiltWorker
class RegisterPrayerTimeWorker @AssistedInject constructor(
    private val apiRepoImp: PrayerTimeRepoImp,
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters
) : CoroutineWorker(
    context,
    workerParams
) {
    private val TAG = "PrayerTimes"
//    private var prayerTimesList: List<Data1> = listOf()

    override suspend fun doWork(): Result {
        val sharedPreferences = PrayersPreferences(this.applicationContext)
        val calendar: Calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH] + 1
        val country = sharedPreferences.city
        val city = sharedPreferences.country
        val method = sharedPreferences.method

//        val api1: PrayerTimesApiService by lazy {
//            ApiServiceCreation().getPrayerTimeApi()
//        }

        return withContext(Dispatchers.IO) {
            when (val api = apiRepoImp.getPrayerTimes(
                year,
                month,
                city ?: "Alexandria",
                country ?: "Cairo",
                method ?: 3
            )
            ) {
                is Resource.Success -> {
                    val data = api.data?.data
                    if (data != null) {
                        if (data.size == 30 || data.size == 31)
                            Log.d(TAG, "onResponse: ${data.size}")
//                    prayerTimesList = data
                        makeWorker(data, year, month)
                    }
                    Result.success()
                }

                is Resource.Error -> {
                    Log.d(TAG, "onFailure: ${api.massage} & Error Code = ${api.data!!.code}")
                    Result.failure()
                }

            }
        }


        /**        api1.getAllPrayerTimesForAzan(
        year,
        month,
        city ?: "Alexandria",
        country ?: "Cairo",
        method!!
        )
        .enqueue(object : Callback<PrayerTimesResponce> {
        override fun onResponse(
        call: Call<PrayerTimesResponce>,
        response: Response<PrayerTimesResponce>
        ) {
        Log.d(TAG, "onResponse: ${call.request().url}")
        val data = response.body()?.data
        if (data != null) {
        if (data.size == 30 || data.size == 31)
        Log.d(TAG, "onResponse: ${data.size}")
        prayerTimesList = data
        makeWorker(data, year, month)
        }
        }

        override fun onFailure(call: Call<PrayerTimesResponce>, t: Throwable) {
        Log.d(TAG, "onFailure: ${t.message}")
        }
        })
         */
    }

    private fun makeWorker(
        data: List<Data1>?,
        year: Int,
        month: Int
    ) {
        if (!data.isNullOrEmpty()) {
            for (i in data.indices) {
                val timings: Timings = data[i].timings
                val prayers = convertFromTimings(timings)
                val day = i + 1
                prayers.forEach { prayerTiming ->
                    val prayerTag = "${year}/${month}/${day}${prayerTiming.prayerName}"
                    val input = Data.Builder()
                        .putString(
                            AzanNotificationConstant().NOTIFICATION_TITTLE_KEY,
                            prayerTiming.prayerName
                        )
                        .putString(
                            AzanNotificationConstant().NOTIFICATION_CONTENT_KEY,
                            "حي علي الصلاة"
                        )
                        .build()

                    val regesterPrayerRequest: OneTimeWorkRequest =
                        OneTimeWorkRequest.Builder(AzanNotificationWorker::class.java)
                            .addTag(prayerTag)
                            .setInitialDelay(
                                calculatePrayerDelay(year, month, day, prayerTiming),
                                TimeUnit.MILLISECONDS
                            )
                            .setInputData(input)
                            .build()
                    WorkManager.getInstance(applicationContext)
                        .enqueueUniqueWork(
                            prayerTag,
                            ExistingWorkPolicy.REPLACE,
                            regesterPrayerRequest
                        )
                }

            }
        }

    }

    @SuppressLint("SimpleDateFormat")
    private fun calculatePrayerDelay(
        year: Int,
        month: Int,
        day: Int,
        prayerTiming: PrayerTiming
    ): Long {
        val pattern = "yyyy/MM/dd HH:mm"
        val decimalFormatter = DecimalFormat("00")
        val formatter = SimpleDateFormat(pattern)
        val time = prayerTiming.prayerTime.split(" ")[0]
        val prayerDate =
            "${year}/${decimalFormatter.format(month)}/${decimalFormatter.format(day)} ${time}"
        val date = formatter.parse(prayerDate)
        val currentTime: Long = System.currentTimeMillis()
        return abs(date?.time?.minus(currentTime) ?: 0)
    }

    private fun convertFromTimings(timings: Timings): ArrayList<PrayerTiming> {
        val res: ArrayList<PrayerTiming> = arrayListOf()

        res.add(PrayerTiming("Fajr", timings.Fajr))
        res.add(PrayerTiming("Dhuhr", timings.Dhuhr))
        res.add(PrayerTiming("Asr", timings.Asr))
        res.add(PrayerTiming("Maghrib", timings.Maghrib))
        res.add(PrayerTiming("Isha", timings.Isha))

        return res

    }


}