package com.example.focusstarthomework.timer.ui

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.*
import com.example.focusstarthomework.R
import com.example.focusstarthomework.utils.makeStatusNotification
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay


class TimerWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    companion object {
        const val TAG = "ForegroundWorker"
        const val NOTIFICATION_ID = 42
        const val CHANNEL_ID = "Job progress"
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private val notificationManager = context.getSystemService(NotificationManager::class.java)
    private val overallTimeInSeconds = inputData.getInt(TIMER_TIME, 0)
    private var currentTime = overallTimeInSeconds


    @RequiresApi(Build.VERSION_CODES.M)
    override suspend fun doWork(): Result {
        return coroutineScope {
            val job = async {
                createNotificationChannel()
                setForeground(ForegroundInfo(NOTIFICATION_ID, createNotification()))

                while (currentTime > 0) {
                    setProgress(workDataOf(TIMER_TIME to currentTime))
                    showProgress(currentTime)
                    delay(1000L)
                    currentTime -= 1
                }

                makeStatusNotification("Время истекло", applicationContext)
            }
            job.await()
            Result.success()
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var notificationChannel = notificationManager?.getNotificationChannel(CHANNEL_ID)
            if (notificationChannel == null) {
                notificationChannel = NotificationChannel(
                    CHANNEL_ID, TAG, NotificationManager.IMPORTANCE_LOW
                )
                notificationManager?.createNotificationChannel(notificationChannel)
            }
        }
    }

    private fun createNotification(): Notification =
        NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Important background job")
            .build()

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showProgress(progress: Int) {
        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Таймер")
            .setContentText(getContentText(progress))
            .setProgress(overallTimeInSeconds, progress, false)
            .build()
        notificationManager?.notify(NOTIFICATION_ID, notification)
    }

    private fun getContentText(timeInSeconds: Int): String =
        when {
            timeInSeconds > 3600 -> "${timeInSeconds / 3600} час. ${timeInSeconds % 3600 / 60} мин. ${timeInSeconds % 60} сек."
            timeInSeconds in 61..3599 -> "${timeInSeconds / 60} мин. ${timeInSeconds % 60} сек."
            else -> "$timeInSeconds сек."
        }
}