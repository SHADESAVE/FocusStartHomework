package com.example.focusstarthomework.loanslist.data

import com.example.focusstarthomework.loanslist.domain.LoansListRepository

class LoansListRepositoryImpl(
    private val loansListDataSource: LoansListDataSourceImpl
): LoansListRepository {
    override fun getLoansList() =
        loansListDataSource.getLoansList()

    override fun getLoansConditions() =
        loansListDataSource.getLoansConditions()
}