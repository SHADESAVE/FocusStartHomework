package com.example.focusstarthomework.loanslist.ui

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.focusstarthomework.R
import com.example.focusstarthomework.authentication.ui.AuthFragment
import com.example.focusstarthomework.loanslist.di.LoansListViewModelFactory
import com.example.focusstarthomework.loanslist.domain.entity.Conditions
import com.example.focusstarthomework.loanslist.presentation.LoansListViewModel
import kotlinx.android.synthetic.main.create_loan_dialog.*
import kotlinx.android.synthetic.main.create_loan_dialog.view.*
import kotlinx.android.synthetic.main.fragment_loans_list.*

class LoansListFragment : Fragment(R.layout.fragment_loans_list) {

    companion object {
        const val LOAN = "LOAN"
    }

    private var token = ""

    private val viewModel: LoansListViewModel by viewModels {
        LoansListViewModelFactory(token)
    }

    private val adapter = LoansListAdapter { loanView -> viewModel.loanClicked(loanView) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.let {
            token = it.getString(AuthFragment.TOKEN_KEY, "")
        }
    }

    //TODO exit, support, filter, swipeOnRefreshLayout, вынести sharedPrefs в датасорс and navigation in viewModel,
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLoansListRecycler()

        create_loan_fab.setOnClickListener {
            viewModel.createLoan()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) { activity?.finish() }

        viewModel.loansList.observe(viewLifecycleOwner, Observer { adapter.setLoansList(it) })
        viewModel.loanClickedEvent.observe(viewLifecycleOwner, Observer { changeFragment(it) })
        viewModel.conditionsReceived.observe(viewLifecycleOwner, Observer { createLoanDialog(it) })
    }

    private fun createLoanDialog(conditions: Conditions) {
        val dialogLayout = LayoutInflater.from(requireContext()).inflate(R.layout.create_loan_dialog, null)
        dialogLayout.create_loan_amount.hint = "Сумма, не более ${conditions.maxAmount}"
        dialogLayout.create_loan_percent.hint = "Под ${conditions.percent}%"
        dialogLayout.create_loan_period.hint = "На срок ${conditions.period}"

        AlertDialog
            .Builder(requireContext())
            .setView(dialogLayout)
            .setPositiveButton("Создать") { _: DialogInterface, _: Int ->
                    viewModel.createLoanFromDialog(
                        dialogLayout.create_loan_amount.text.trim().toString(),
                        dialogLayout.create_loan_percent.text.trim().toString(),
                        dialogLayout.create_loan_period.text.trim().toString()
                    )
                }
            .setNegativeButton("Отмена") { _: DialogInterface, _: Int -> }
            .create()
        .show()
    }

    private fun initLoansListRecycler() {
        loans_list_recycler.layoutManager = LinearLayoutManager(requireContext())
        loans_list_recycler.adapter = adapter
        loans_list_recycler.setHasFixedSize(true)
    }

    private fun changeFragment(loanView: LoanView) {
        findNavController().navigate(
            R.id.action_loansListFragment_to_loanFragment,
            Bundle().apply { putParcelable(LOAN, loanView) })
    }
}