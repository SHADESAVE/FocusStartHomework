package com.example.focusstarthomework.timer.ui

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.focusstarthomework.utils.makeStatusNotification

class TimerWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {

        val appContext = applicationContext

        return try {
            makeStatusNotification("Время истекло", appContext)
            Result.success()
        } catch (error: Throwable) {
            Log.e("TimerWorkerFailure:", error.toString())
            Result.failure()
        }
    }
}