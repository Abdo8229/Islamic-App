package com.example.islamapplictation.prayersnotifivcation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.islamapplictation.R
import com.example.islamapplictation.data.pojo.azan.AzanNotificationConstant

class AzanNotificationWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    private final val CHANNEL_ID = "AZAN_CHANNEL"
    private final val CHANNEL_NAME = "Azan channel"
    val audioAttributes: AudioAttributes = AudioAttributes
        .Builder()
        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
        .setUsage(AudioAttributes.USAGE_NOTIFICATION)
        .build()

    private fun send(tittle: String, content: String, sound: Uri) {
        val notificationManager: NotificationManager =
            getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationBuilder: NotificationCompat.Builder = createNotificationBuilder(content, tittle, sound)
        createNotificationChannel(notificationManager, sound)
        notificationManager.notify(0, notificationBuilder.build())
    }

    private fun createNotificationBuilder(
        content: String,
        tittle: String,
        sound: Uri
    ): NotificationCompat.Builder {
        val notificationBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)

        return notificationBuilder.apply {
            setContentTitle(tittle)
            setContentText(content)
            setSound(sound)
            setSmallIcon(R.mipmap.ic_launcher)
            setPriority(NotificationCompat.PRIORITY_HIGH)
        }
    }

    private fun createNotificationChannel(
        manager: NotificationManager, sound: Uri
    ) {
        val notificationChannel: NotificationChannel =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                NotificationChannel(
                    CHANNEL_ID, CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
                )

            } else {
                return
            }
        notificationChannel.setSound(sound, audioAttributes)
        manager.createNotificationChannel(notificationChannel)

    }

    override fun doWork(): Result {
        val azanSound: Uri =
            Uri.parse("android.resource://${applicationContext.packageName}/${R.raw.azan}")
        val input: Data = inputData
        val tittle: String = input.getString(AzanNotificationConstant().NOTIFICATION_TITTLE_KEY)!!
        val content: String = input.getString(AzanNotificationConstant().NOTIFICATION_CONTENT_KEY)!!
        send(tittle, content, azanSound)
        return Result.success()
    }
}