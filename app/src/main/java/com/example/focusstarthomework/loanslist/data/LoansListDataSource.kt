package com.example.focusstarthomework.loanslist.data

import com.example.focusstarthomework.loanslist.domain.entity.Conditions
import com.example.focusstarthomework.loanslist.domain.entity.Loan
import io.reactivex.Single

interface LoansListDataSource {
    fun getLoansList(): Single<List<Loan>>
    fun getLoansConditions(): Single<Conditions>
}

class LoansListDataSourceImpl(private val loansListApi: LoansListApi) : LoansListDataSource {
    override fun getLoansList() =
        loansListApi.getLoansList()

    override fun getLoansConditions() =
        loansListApi.getLoansConditions()
}

