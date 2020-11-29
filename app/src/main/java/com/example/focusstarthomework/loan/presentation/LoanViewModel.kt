package com.example.focusstarthomework.loan.presentation

import androidx.lifecycle.ViewModel
import com.example.focusstarthomework.loan.domain.GetAllLoansUseCase
import io.reactivex.disposables.Disposable

class LoanViewModel(
    private val getAllLoansUseCase: GetAllLoansUseCase
) : ViewModel() {

    private var disposable: Disposable? = null

    override fun onCleared() {
        disposable?.dispose()
        disposable = null
        super.onCleared()
    }
}