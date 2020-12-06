package com.example.focusstarthomework.loanslist.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.focusstarthomework.loanslist.domain.GetLoansConditionsUseCase
import com.example.focusstarthomework.loanslist.domain.GetLoansListUseCase
import com.example.focusstarthomework.loanslist.domain.entity.Conditions
import com.example.focusstarthomework.loanslist.domain.entity.Loan
import com.example.focusstarthomework.loanslist.ui.LoanView
import com.example.focusstarthomework.utils.SingleLiveEvent
import com.example.focusstarthomework.utils.toLoanView
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class LoansListViewModel(
    private val getLoansListUseCase: GetLoansListUseCase,
    private val getLoansConditionsUseCase: GetLoansConditionsUseCase
) : ViewModel() {

    private var compositeDisposable = CompositeDisposable()

    val loansList: MutableLiveData<List<LoanView>> = MutableLiveData()
    val loanClickedEvent = SingleLiveEvent<LoanView>()
    val conditionsReceived = SingleLiveEvent<Conditions>()

    init {
        getLoansList()
    }

    private fun getLoansList() {
        getLoansListUseCase()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<Loan>> {
                override fun onSuccess(t: List<Loan>) {
                    loansList.value = t.toLoanView()
                }

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onError(e: Throwable) {
                    Log.e("LoansList request error", e.message.toString())
                }
            })
    }

    fun loanClicked(loanView: LoanView) {
        loanClickedEvent.value = loanView
    }

    fun createLoan() {
        getLoansConditions()
    }

    private fun getLoansConditions() {
        getLoansConditionsUseCase()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Conditions> {
                override fun onSuccess(t: Conditions) {
                    conditionsReceived.value = t
                }

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onError(e: Throwable) {
                    Log.e("Get conditions error", e.message.toString())
                }
            })
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }


}
