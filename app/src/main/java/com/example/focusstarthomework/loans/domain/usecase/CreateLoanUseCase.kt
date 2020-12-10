package com.example.focusstarthomework.loans.domain.usecase

import com.example.focusstarthomework.loans.domain.LoansRepository
import com.example.focusstarthomework.loans.domain.entity.NewLoanDTO

class CreateLoanUseCase(private val loansRepository: LoansRepository) {

    operator fun invoke(newLoanDTO: NewLoanDTO) =
        loansRepository.createNewLoan(newLoanDTO)
}
