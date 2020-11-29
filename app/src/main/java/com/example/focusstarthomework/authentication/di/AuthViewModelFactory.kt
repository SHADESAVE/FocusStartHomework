package com.example.focusstarthomework.authentication.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.focusstarthomework.authentication.data.AuthApi
import com.example.focusstarthomework.authentication.data.AuthDataSourceImpl
import com.example.focusstarthomework.authentication.data.AuthRepositoryImpl
import com.example.focusstarthomework.authentication.domain.LoginUseCase
import com.example.focusstarthomework.authentication.domain.RegisterUseCase
import com.example.focusstarthomework.authentication.presentation.AuthViewModel
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class AuthViewModelFactory() : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass == AuthViewModel::class.java) {

            val authDataSource = AuthDataSourceImpl(AuthApi.create())
            val authRepository = AuthRepositoryImpl(authDataSource)

            return AuthViewModel(
                loginUseCase = LoginUseCase(authRepository),
                registerUseCase = RegisterUseCase(authRepository)
            ) as T
        } else {
            error("Unexpected class $modelClass")
        }
    }
}