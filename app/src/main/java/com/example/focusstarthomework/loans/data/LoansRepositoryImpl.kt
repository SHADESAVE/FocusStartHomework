package com.example.focusstarthomework.loans.data

import com.example.focusstarthomework.loans.domain.LoansRepository
import com.example.focusstarthomework.loans.domain.entity.LoanDTO
import com.example.focusstarthomework.loans.domain.entity.NewLoanDTO
import io.reactivex.Single

class LoansRepositoryImpl(
    private val loansDataSource: LoansDataSourceImpl
) : LoansRepository {

    override fun createNewLoan(newLoanDTO: NewLoanDTO) =
        loansDataSource.createNewLoan(newLoanDTO)

    override fun getLoansList() =
        loansDataSource.getLoansList()

    override fun getLoanById(id: Long): Single<LoanDTO> =
        loansDataSource.getLoanById(id)

    override fun getLoanConditions() =
        loansDataSource.getLoanConditions()

//    override fun saveTokenInSharedPrefs(token: String, tokenKey: String, prefsKey: String) {
//        localDataSource.saveToken(token, tokenKey, prefsKey)
//    }
//
//    override fun getTokenFromSharedPrefs(tokenKey: String, prefsKey: String): String? =
//        localDataSource.getToken(tokenKey, prefsKey)

}