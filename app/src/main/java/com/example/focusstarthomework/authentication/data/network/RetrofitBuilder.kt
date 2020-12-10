package com.example.focusstarthomework.authentication.data.network

import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    fun <T> create(client: OkHttpClient, api: Class<T>): T =
        Retrofit.Builder()
            .baseUrl("http://focusapp-env.eba-xm2atk2z.eu-central-1.elasticbeanstalk.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
            .create(api)
}