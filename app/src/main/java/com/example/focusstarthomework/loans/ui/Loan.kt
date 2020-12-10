package com.example.focusstarthomework.loans.ui

data class Loan(
    val amount: Double,
    val percent: Double,
    val period: Int,
    val date: String,
    val fullName: String,
    val id: Long,
    val phoneNumber: String,
    val state: String,
    val stateColor: Int
)