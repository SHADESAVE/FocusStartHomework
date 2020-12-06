package com.example.focusstarthomework.loanslist.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.focusstarthomework.loanslist.data.LoansListApi
import com.example.focusstarthomework.loanslist.data.LoansListDataSourceImpl
import com.example.focusstarthomework.loanslist.data.LoansListRepositoryImpl
import com.example.focusstarthomework.loanslist.domain.GetLoansConditionsUseCase
import com.example.focusstarthomework.loanslist.domain.GetLoansListUseCase
import com.example.focusstarthomework.loanslist.presentation.LoansListViewModel

class LoansListViewModelFactory(private val token: String) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass == LoansListViewModel::class.java) {

            val loansListDataSource = LoansListDataSourceImpl(LoansListApi.create(token))
            val loansListRepository = LoansListRepositoryImpl(loansListDataSource)

            return LoansListViewModel(
                getLoansListUseCase = GetLoansListUseCase(loansListRepository),
                getLoansConditionsUseCase = GetLoansConditionsUseCase(loansListRepository)
            ) as T
        } else {
            error("Unexpected class $modelClass")
        }
    }
}