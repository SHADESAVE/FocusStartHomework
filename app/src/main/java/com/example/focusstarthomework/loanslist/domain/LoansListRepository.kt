package com.example.focusstarthomework.loanslist.domain

import com.example.focusstarthomework.loanslist.domain.entity.Conditions
import com.example.focusstarthomework.loanslist.domain.entity.Loan
import io.reactivex.Single

interface LoansListRepository {
    fun getLoansList(): Single<List<Loan>>
    fun getLoansConditions(): Single<Conditions>
}