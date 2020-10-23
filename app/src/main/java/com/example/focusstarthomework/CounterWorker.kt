package com.example.focusstarthomework

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.focusstarthomework.utils.makeStatusNotification

class CounterWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {

        val appContext = applicationContext

        makeStatusNotification("Starting count", appContext)

        return try {
            var counter = 0
            for (i in 0..20) {
                Thread.sleep(1_000)
                counter++
                makeStatusNotification("Counter = $counter", appContext)
            }
            Result.success()
        } catch (error: Throwable) {
            Log.e("WorkerFailure:", error.toString())
            Result.failure()
        }
    }
}