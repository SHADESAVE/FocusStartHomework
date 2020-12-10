package com.example.focusstarthomework.loans.data

import com.example.focusstarthomework.loans.data.network.LoansApi
import com.example.focusstarthomework.loans.domain.entity.Conditions
import com.example.focusstarthomework.loans.domain.entity.LoanDTO
import com.example.focusstarthomework.loans.domain.entity.NewLoanDTO
import io.reactivex.Single

interface LoansDataSource {
    fun createNewLoan(newLoanDTO: NewLoanDTO): Single<LoanDTO>
    fun getLoansList(): Single<List<LoanDTO>>
    fun getLoanById(id: Long): Single<LoanDTO>
    fun getLoanConditions(): Single<Conditions>
}

class LoansDataSourceImpl(private val loansApi: LoansApi) : LoansDataSource {
    override fun createNewLoan(newLoanDTO: NewLoanDTO) =
        loansApi.createNewLoan(newLoanDTO)

    override fun getLoansList() =
        loansApi.getLoansList()

    override fun getLoanById(id: Long) =
        loansApi.getLoanById(id)

    override fun getLoanConditions() =
        loansApi.getLoanConditions()
}

