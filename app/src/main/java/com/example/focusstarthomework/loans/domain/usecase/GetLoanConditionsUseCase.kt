package com.example.focusstarthomework.loans.domain.usecase

import com.example.focusstarthomework.loans.domain.LoansRepository

class GetLoanConditionsUseCase(private val loansRepository: LoansRepository) {

    operator fun invoke() = loansRepository.getLoanConditions()
}