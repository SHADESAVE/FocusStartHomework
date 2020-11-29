package com.example.focusstarthomework.loan.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.focusstarthomework.R
import com.example.focusstarthomework.loan.di.LoanViewModelFactory
import com.example.focusstarthomework.loan.presentation.LoanViewModel

class LoanFragment : Fragment(R.layout.fragment_loan) {

    private val viewModel: LoanViewModel by viewModels {
        LoanViewModelFactory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}