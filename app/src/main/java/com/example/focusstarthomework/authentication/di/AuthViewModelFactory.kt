package com.example.focusstarthomework.authentication.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.focusstarthomework.authentication.data.AuthApi
import com.example.focusstarthomework.authentication.data.AuthDataSourceImpl
import com.example.focusstarthomework.authentication.data.AuthRepositoryImpl
import com.example.focusstarthomework.authentication.domain.LoginUseCase
import com.example.focusstarthomework.authentication.domain.RegisterUseCase
import com.example.focusstarthomework.authentication.presentation.AuthViewModel


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