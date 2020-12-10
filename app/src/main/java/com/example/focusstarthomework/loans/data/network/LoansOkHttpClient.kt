package com.example.focusstarthomework.loans.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object LoansOkHttpClient {
    fun create(token: String): OkHttpClient {
        val interceptor =
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor {
                it.proceed(
                    it.request().newBuilder().addHeader("Authorization", token).build()
                )
            }
            .build()
    }
}
