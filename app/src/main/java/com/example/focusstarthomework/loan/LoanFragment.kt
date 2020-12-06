package com.example.focusstarthomework.loan

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.focusstarthomework.R
import com.example.focusstarthomework.loanslist.ui.LoanView
import com.example.focusstarthomework.loanslist.ui.LoansListFragment
import com.example.focusstarthomework.utils.LoanState
import kotlinx.android.synthetic.main.fragment_loan.*

class LoanFragment : Fragment(R.layout.fragment_loan) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loan = arguments?.getParcelable(LoansListFragment.LOAN) as LoanView?
        setupLoan(loan)
    }

    private fun setupLoan(loan: LoanView?) {
        if (loan != null) {
            state.text = loan.state
            state.setTextColor(loan.stateColor)
            full_name.text = loan.fullName
            amount_percent_period.text = loan.amountPercentPeriod
            phone_number.text = loan.phoneNumber

            setupLoanDescription(loan.state)
        } else {
            loan_layout.visibility = View.GONE
            loan_err_text.visibility = View.VISIBLE
        }
    }

    private fun setupLoanDescription(state: String) {
        when (state) {
            LoanState.APPROVED.string -> loan_description.text = getString(R.string.get_loan_guide)
            LoanState.REJECTED.string -> loan_description.text = getString(R.string.loan_rejected)
            LoanState.REGISTERED.string -> loan_description.text = getString(R.string.loan_registered)
            else -> {
            }
        }
    }
}