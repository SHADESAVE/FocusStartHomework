package com.example.focusstarthomework.loans.domain.usecase

import com.example.focusstarthomework.loans.domain.LoansRepository

class GetLoanByIdUseCase(private val loansRepository: LoansRepository) {

    operator fun invoke(id: Long) =
        loansRepository.getLoanById(id)

}
