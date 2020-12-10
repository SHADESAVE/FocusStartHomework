package com.example.focusstarthomework.loans.domain.entity

data class NewLoanDTO(
    val amount: Double,
    val firstName: String,
    val lastName: String,
    val percent: Double,
    val period: Int,
    val phoneNumber: String
)