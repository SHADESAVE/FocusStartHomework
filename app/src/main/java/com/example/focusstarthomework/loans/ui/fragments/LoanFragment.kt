package com.example.focusstarthomework.loans.ui.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.focusstarthomework.R
import com.example.focusstarthomework.authentication.presentation.LoadingState
import com.example.focusstarthomework.authentication.ui.AuthFragment
import com.example.focusstarthomework.loans.di.LoansViewModelFactory
import com.example.focusstarthomework.loans.presentation.LoanState
import com.example.focusstarthomework.loans.presentation.LoansViewModel
import com.example.focusstarthomework.loans.ui.Loan
import com.example.focusstarthomework.utils.ErrorState
import kotlinx.android.synthetic.main.fragment_loan.*


class LoanFragment : Fragment(R.layout.fragment_loan) {

    private val viewModel: LoansViewModel by viewModels {
        LoansViewModelFactory(getTokenFromArgs(), requireContext())
    }

    private fun getTokenFromArgs() =
        arguments?.getString(AuthFragment.TOKEN_KEY, "").toString()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        var loanId: Long? = null

        viewModel.loanReceived.observe(viewLifecycleOwner, Observer(::setupLoan))
        viewModel.loadingEvent.observe(viewLifecycleOwner, Observer(::loadingManager))
        viewModel.errorEvent.observe(viewLifecycleOwner, Observer(::showError))
        viewModel.setupNavController(findNavController())

        arguments?.getLong(LoansListFragment.LOAN_ID)?.let {
            loanId = it
            viewModel.getLoanById(it)
        }

        retry_button.setOnClickListener {
            loanId?.let {
                viewModel.getLoanById(it)
            }
        }

    }

    private fun setupLoan(loan: Loan) {
        state.text = loan.state
        state.setTextColor(loan.stateColor)

        amount_percent_period.text =
            getString(R.string.loan_amount_percent_period, loan.amount, loan.percent, loan.period)

        date.text = loan.date
        full_name.text = loan.fullName
        phone_number.text = loan.phoneNumber

        when (loan.state) {
            LoanState.APPROVED.name -> loan_description.text =
                getString(R.string.get_loan_guide)
            LoanState.REJECTED.name -> loan_description.text =
                getString(R.string.loan_rejected)
            LoanState.REGISTERED.name -> loan_description.text =
                getString(R.string.loan_registered)
            else -> {
            }
        }
    }

    private fun loadingManager(loadingState: LoadingState) {
        when (loadingState) {
            LoadingState.LOADING -> {
                loading.visibility = View.VISIBLE
                retry_button.visibility = View.GONE
                loan_layout.visibility = View.GONE
            }
            LoadingState.DONE -> {
                loading.visibility = View.GONE
                retry_button.visibility = View.GONE
                loan_layout.visibility = View.VISIBLE
            }
            LoadingState.ERROR -> {
                loading.visibility = View.GONE
                retry_button.visibility = View.VISIBLE
                loan_layout.visibility = View.GONE
            }
        }
    }

    private fun showError(errorState: ErrorState) {
        when (errorState) {
            ErrorState.HTTP401 -> viewModel.showToast(
                requireContext(),
                getString(R.string.error_http_401)
            )
            ErrorState.HTTP403 -> viewModel.showToast(
                requireContext(),
                getString(R.string.error_http_403)
            )
            ErrorState.HTTP404 -> viewModel.showToast(
                requireContext(),
                getString(R.string.error_http_404)
            )
            ErrorState.UNKNOWN_HOST -> viewModel.showToast(
                requireContext(),
                getString(R.string.error_unknown_host)
            )
            ErrorState.UNHANDLED_ERROR -> viewModel.showToast(
                requireContext(),
                getString(R.string.error_unhandled_error)
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.loan_fragment_menu, menu)
        menu.findItem(R.id.menu_filter_group).isVisible = false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_help -> {
                viewModel.showHelpDialog(requireContext())
                true
            }
            R.id.menu_exit -> {
                viewModel.clearTokenAndChangeFragment(R.id.action_loanFragment_to_authFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}