package com.example.focusstarthomework.loanslist.domain

class GetLoansConditionsUseCase(private val loansListRepository: LoansListRepository) {

    operator fun invoke()
            = loansListRepository.getLoansConditions()
}