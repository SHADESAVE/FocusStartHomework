package com.example.focusstarthomework.timer.ui

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.focusstarthomework.utils.makeStatusNotification
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

class TimerWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val appContext = applicationContext


        return coroutineScope {
            val job = async {
                var time = inputData.getLong(TIMER_TIME, 0L)
                while (time > 0) {
                    delay(1000)
                    time -= 1000
                }
                makeStatusNotification("Время истекло воркер $time", appContext)
            }
            job.await()
            Result.success()
        }
    }
}