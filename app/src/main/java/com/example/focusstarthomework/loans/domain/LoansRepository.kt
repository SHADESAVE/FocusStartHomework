package com.example.focusstarthomework.loans.domain

import com.example.focusstarthomework.loans.domain.entity.Conditions
import com.example.focusstarthomework.loans.domain.entity.LoanDTO
import com.example.focusstarthomework.loans.domain.entity.NewLoanDTO
import io.reactivex.Single

interface LoansRepository {
    fun createNewLoan(newLoanDTO: NewLoanDTO): Single<LoanDTO>
    fun getLoansList(): Single<List<LoanDTO>>
    fun getLoanById(id: Long): Single<LoanDTO>
    fun getLoanConditions(): Single<Conditions>
}