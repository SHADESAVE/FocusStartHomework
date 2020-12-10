package com.example.focusstarthomework.loans.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.focusstarthomework.R
import com.example.focusstarthomework.authentication.presentation.LoadingState
import com.example.focusstarthomework.authentication.ui.AuthFragment
import com.example.focusstarthomework.loans.di.LoansViewModelFactory
import com.example.focusstarthomework.loans.presentation.LoanState
import com.example.focusstarthomework.loans.presentation.LoansViewModel
import com.example.focusstarthomework.loans.ui.LoansListAdapter
import com.example.focusstarthomework.utils.ErrorState
import kotlinx.android.synthetic.main.fragment_loans_list.*

class LoansListFragment : Fragment(R.layout.fragment_loans_list) {

    companion object {
        const val LOAN_ID = "LOAN_ID"
    }

    private val viewModel: LoansViewModel by viewModels {
        LoansViewModelFactory(getTokenFromArgs(), requireContext())
    }

    private val adapter = LoansListAdapter { loan ->
        Log.d("LoanClicked", "true")
        viewModel.changeFragmentToLoan(loan.id)
    }

    private fun getTokenFromArgs() =
        arguments?.getString(AuthFragment.TOKEN_KEY, "").toString()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        loans_list_recycler.layoutManager = LinearLayoutManager(requireContext())
        loans_list_recycler.adapter = adapter
        loans_list_recycler.setHasFixedSize(true)

        arguments?.apply {
            if (getBoolean(AuthFragment.NEW_USER))
                viewModel.showHelpDialog(requireContext())
            remove(AuthFragment.NEW_USER)
        }

        viewModel.loansListReceived.observe(
            viewLifecycleOwner,
            Observer {
                adapter.setLoansList(it)
            })
        viewModel.loanCreated.observe(
            viewLifecycleOwner,
            Observer {
                viewModel.showToast(
                    requireContext(),
                    getString(R.string.create_loan_is_successful)
                )
            })
        viewModel.amountErrorReceived.observe(
            viewLifecycleOwner,
            Observer {
                viewModel.showToast(
                    requireContext(),
                    getString(R.string.amount_error)
                )
            })
        viewModel.conditionsReceived.observe(viewLifecycleOwner, Observer {
            viewModel.showLoanDialog(requireContext())
        })
        viewModel.loadingEvent.observe(viewLifecycleOwner, Observer(::loadingManager))
        viewModel.errorEvent.observe(viewLifecycleOwner, Observer(::showError))
        viewModel.setupNavController(findNavController())
        viewModel.getLoansList()

        retry_button.setOnClickListener { viewModel.getLoansList() }
        create_loan_fab.setOnClickListener {
            it.visibility = View.GONE
            viewModel.getLoansConditions()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            activity?.finish()
        }
    }

    private fun loadingManager(loadingState: LoadingState) {
        when (loadingState) {
            LoadingState.LOADING -> {
                loading.visibility = View.VISIBLE
                loans_list_recycler.visibility = View.GONE
                create_loan_fab.visibility = View.GONE
                retry_button.visibility = View.GONE
            }
            LoadingState.DONE -> {
                loading.visibility = View.GONE
                loans_list_recycler.visibility = View.VISIBLE
                create_loan_fab.visibility = View.VISIBLE
                retry_button.visibility = View.GONE
            }
            LoadingState.ERROR -> {
                loading.visibility = View.GONE
                loans_list_recycler.visibility = View.GONE
                create_loan_fab.visibility = View.GONE
                retry_button.visibility = View.VISIBLE
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
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_filter_all -> {
                viewModel.filterChanged(LoanState.ALL)
                true
            }
            R.id.menu_filter_approved -> {
                viewModel.filterChanged(LoanState.APPROVED)
                true
            }
            R.id.menu_filter_registered -> {
                viewModel.filterChanged(LoanState.REGISTERED)
                true
            }
            R.id.menu_filter_rejected -> {
                viewModel.filterChanged(LoanState.REJECTED)
                true
            }
            R.id.menu_help -> {
                viewModel.showHelpDialog(requireContext())
                true
            }
            R.id.menu_exit -> {
                viewModel.clearTokenAndChangeFragment(R.id.action_loansListFragment_to_authFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}