package com.example.focusstarthomework.authentication.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object AuthOkHttpClient {
    fun create(): OkHttpClient {
        val interceptor =
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }
}
