package com.example.focusstarthomework.authentication.data

import com.example.focusstarthomework.authentication.domain.entity.RegisteredInUser
import com.example.focusstarthomework.authentication.domain.entity.User
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("/login")
    fun login(@Body user: User): Single<ResponseBody>

    @POST("/registration")
    fun register(@Body user: User): Single<RegisteredInUser>

    companion object Factory {
        fun create(): AuthApi {
            val interceptor =
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

            return Retrofit.Builder()
                .baseUrl("http://focusapp-env.eba-xm2atk2z.eu-central-1.elasticbeanstalk.com/")
                .client(
                    OkHttpClient.Builder()
                        .addInterceptor(interceptor)
                        .build()
                )
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()
                .create(AuthApi::class.java)
        }
    }
}