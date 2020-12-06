package com.example.focusstarthomework.loanslist.data

import com.example.focusstarthomework.loanslist.domain.entity.Conditions
import com.example.focusstarthomework.loanslist.domain.entity.Loan
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface LoansListApi {

    @GET("/loans/all")
    fun getLoansList(): Single<List<Loan>>

    @GET("/loans/conditions")
    fun getLoansConditions(): Single<Conditions>

    companion object Factory {
        fun create(token: String): LoansListApi {
            val interceptor =
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

            return Retrofit.Builder()
                .baseUrl("http://focusapp-env.eba-xm2atk2z.eu-central-1.elasticbeanstalk.com/")
                .client(
                    OkHttpClient.Builder()
                        .addInterceptor(interceptor)
                        .addInterceptor {
                            it.proceed(it.request().newBuilder().addHeader("Authorization", token).build())
                        }
                        .build()
                )
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()
                .create(LoansListApi::class.java)
        }
    }
}