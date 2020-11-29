package com.example.focusstarthomework.loan.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.focusstarthomework.loan.domain.GetAllLoansUseCase
import com.example.focusstarthomework.loan.presentation.LoanViewModel

class LoanViewModelFactory() : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass == LoanViewModel::class.java) {

            val uploadImageUseCase = GetAllLoansUseCase()

            return LoanViewModel(uploadImageUseCase) as T
        } else {
            error("Unexpected class $modelClass")
        }
    }
}