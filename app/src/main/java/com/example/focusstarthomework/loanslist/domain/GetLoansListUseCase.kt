package com.example.focusstarthomework.loanslist.domain

class GetLoansListUseCase(private val loansListRepository: LoansListRepository) {

    operator fun invoke()
            = loansListRepository.getLoansList()

}