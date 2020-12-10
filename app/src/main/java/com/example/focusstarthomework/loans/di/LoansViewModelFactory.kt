package com.example.focusstarthomework.loans.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.focusstarthomework.authentication.data.AuthLocalDataSourceImpl
import com.example.focusstarthomework.authentication.data.AuthLocalRepositoryImpl
import com.example.focusstarthomework.authentication.data.local.SharedPreferencesProvider
import com.example.focusstarthomework.authentication.data.network.RetrofitBuilder
import com.example.focusstarthomework.loans.data.LoansDataSourceImpl
import com.example.focusstarthomework.loans.data.LoansRepositoryImpl
import com.example.focusstarthomework.loans.data.network.LoansApi
import com.example.focusstarthomework.loans.data.network.LoansOkHttpClient
import com.example.focusstarthomework.loans.domain.usecase.*
import com.example.focusstarthomework.loans.presentation.LoansViewModel

class LoansViewModelFactory(
    private val token: String,
    private val context: Context
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass == LoansViewModel::class.java) {

            val loansApi =
                RetrofitBuilder.create(LoansOkHttpClient.create(token), LoansApi::class.java)

            val remoteDataSource = LoansDataSourceImpl(loansApi)
            val remoteRepository = LoansRepositoryImpl(remoteDataSource)

            val localDataSource = AuthLocalDataSourceImpl(SharedPreferencesProvider(context))
            val localRepository = AuthLocalRepositoryImpl(localDataSource)

            return LoansViewModel(
                createLoanUseCase = CreateLoanUseCase(
                    remoteRepository
                ),
                getLoansListUseCase = GetLoansListUseCase(
                    remoteRepository
                ),
                getLoanByIdUseCase = GetLoanByIdUseCase(
                    remoteRepository
                ),
                getLoanConditionsUseCase = GetLoanConditionsUseCase(
                    remoteRepository
                ),
                clearTokenUseCase = ClearTokenUseCase(
                    localRepository
                ),
                token = token
            ) as T
        } else {
            error("Unexpected class $modelClass")
        }
    }
}