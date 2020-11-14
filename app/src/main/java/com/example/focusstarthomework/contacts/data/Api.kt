package com.example.focusstarthomework.contacts.data

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

const val CLIENT_ID = "CLIENT_ID"

interface Api {

    @Headers("Authorization: Client-ID {{$CLIENT_ID}}")
    @POST("upload")
    suspend fun uploadImage(
        @Body part: MultipartBody
    ): ResponseBody
}