package com.example.focusstarthomework.authentication.data.network

import com.example.focusstarthomework.authentication.domain.entity.RegisteredInUser
import com.example.focusstarthomework.authentication.domain.entity.User
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("/login")
    fun login(@Body user: User): Single<ResponseBody>

    @POST("/registration")
    fun register(@Body user: User): Single<RegisteredInUser>

}