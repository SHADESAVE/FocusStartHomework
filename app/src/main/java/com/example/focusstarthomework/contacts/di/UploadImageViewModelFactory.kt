package com.example.focusstarthomework.contacts.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.focusstarthomework.contacts.data.Api
import com.example.focusstarthomework.contacts.data.RepositoryImpl
import com.example.focusstarthomework.contacts.data.NetworkDataSourceImpl
import com.example.focusstarthomework.contacts.domain.UploadImageUseCase
import com.example.focusstarthomework.contacts.presentation.UploadImageViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UploadImageViewModelFactory(
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass == UploadImageViewModel::class.java) {

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.imgur.com/3/")
                .client(client)
                .build()
            val api = retrofit.create(Api::class.java)

            val networkDataSource = NetworkDataSourceImpl(api)
            val repository = RepositoryImpl(networkDataSource)
            val uploadImageUseCase = UploadImageUseCase(repository)

            return UploadImageViewModel(
                uploadImageUseCase
            ) as T
        } else {
            error("Unexpected class $modelClass")
        }
    }
}