package com.example.focusstarthomework.utils

import android.graphics.Color
import com.example.focusstarthomework.loanslist.domain.entity.Loan
import com.example.focusstarthomework.loanslist.ui.LoanView

fun List<Loan>.toLoanView() =
    this.map {
        when (it.state) {
            LoanState.APPROVED.string ->
                getLoanView(it, Color.GREEN)
            LoanState.REGISTERED.string ->
                getLoanView(it, Color.LTGRAY)
            LoanState.REJECTED.string ->
                getLoanView(it, Color.RED)
            else -> getLoanView(it, Color.LTGRAY)
        }
    }

private fun getLoanView(loan: Loan, color: Int) = LoanView(
    amountPercentPeriod = "${loan.amount} руб. под ${loan.percent}% на ${loan.period} мес.",
    date = loan.date,
    fullName = "${loan.firstName} ${loan.lastName}",
    id = loan.id,
    phoneNumber = loan.phoneNumber,
    state = loan.state,
    stateColor = color
)

enum class LoanState(val string: String) {
    APPROVED("APPROVED"), REGISTERED("REGISTERED"), REJECTED("REJECTED")
}