package com.example.focusstarthomework.loans.presentation

import android.graphics.Color
import com.example.focusstarthomework.loans.domain.entity.LoanDTO
import com.example.focusstarthomework.loans.ui.Loan
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

fun List<LoanDTO>.toLoanList() =
    this.map { it.toLoan() }

fun LoanDTO.toLoan() =
    when (this.state) {
        LoanState.APPROVED.name ->
            getLoan(this, Color.GREEN)
        LoanState.REGISTERED.name ->
            getLoan(this, Color.GRAY)
        LoanState.REJECTED.name ->
            getLoan(this, Color.RED)
        else -> getLoan(this, Color.GRAY)
    }

private fun getLoan(loanDTO: LoanDTO, color: Int) =
    Loan(
        amount = loanDTO.amount,
        percent = loanDTO.percent,
        period = loanDTO.period,
        date = transformDate(loanDTO.date),
        fullName = "${loanDTO.firstName} ${loanDTO.lastName}",
        id = loanDTO.id,
        phoneNumber = loanDTO.phoneNumber,
        state = loanDTO.state,
        stateColor = color
    )

private fun transformDate(date: String) =
    OffsetDateTime.parse(date)
        .format(
            DateTimeFormatter
                .ofLocalizedDateTime(FormatStyle.MEDIUM)
                .withLocale(Locale.US)
        )
        .toString()
