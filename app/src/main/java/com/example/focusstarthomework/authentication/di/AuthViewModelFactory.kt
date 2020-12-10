package com.example.focusstarthomework.authentication.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.focusstarthomework.authentication.data.AuthLocalDataSourceImpl
import com.example.focusstarthomework.authentication.data.AuthLocalRepositoryImpl
import com.example.focusstarthomework.authentication.data.AuthRemoteDataSourceImpl
import com.example.focusstarthomework.authentication.data.AuthRemoteRepositoryImpl
import com.example.focusstarthomework.authentication.data.local.SharedPreferencesProvider
import com.example.focusstarthomework.authentication.data.network.AuthApi
import com.example.focusstarthomework.authentication.data.network.AuthOkHttpClient
import com.example.focusstarthomework.authentication.data.network.RetrofitBuilder
import com.example.focusstarthomework.authentication.domain.usecase.GetTokenUseCase
import com.example.focusstarthomework.authentication.domain.usecase.LoginUseCase
import com.example.focusstarthomework.authentication.domain.usecase.RegisterUseCase
import com.example.focusstarthomework.authentication.domain.usecase.SaveTokenUseCase
import com.example.focusstarthomework.authentication.presentation.AuthViewModel


class AuthViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass == AuthViewModel::class.java) {

            val authApi = RetrofitBuilder.create(AuthOkHttpClient.create(), AuthApi::class.java)

            val remoteDataSource = AuthRemoteDataSourceImpl(authApi)
            val remoteRepository = AuthRemoteRepositoryImpl(remoteDataSource)

            val localDataSource = AuthLocalDataSourceImpl(SharedPreferencesProvider(context))
            val localRepository = AuthLocalRepositoryImpl(localDataSource)

            return AuthViewModel(
                loginUseCase = LoginUseCase(remoteRepository),
                registerUseCase = RegisterUseCase(remoteRepository),
                saveTokenUseCase = SaveTokenUseCase(localRepository),
                getTokenUseCase = GetTokenUseCase(localRepository)
            ) as T
        } else {
            error("Unexpected class $modelClass")
        }
    }
}