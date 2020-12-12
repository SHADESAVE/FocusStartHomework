package com.example.focusstarthomework.loans.presentation

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.focusstarthomework.R
import com.example.focusstarthomework.authentication.presentation.LoadingState
import com.example.focusstarthomework.authentication.ui.AuthFragment
import com.example.focusstarthomework.loans.domain.entity.Conditions
import com.example.focusstarthomework.loans.domain.entity.NewLoanDTO
import com.example.focusstarthomework.loans.domain.usecase.*
import com.example.focusstarthomework.loans.ui.Loan
import com.example.focusstarthomework.loans.ui.fragments.LoansListFragment
import com.example.focusstarthomework.utils.ErrorState
import com.example.focusstarthomework.utils.SingleLiveEvent
import com.example.focusstarthomework.utils.handleNetworkError
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.get_loan_amount_dialog.view.*
import kotlinx.android.synthetic.main.get_personal_data_dialog.view.*

class LoansViewModel(
    private val createLoanUseCase: CreateLoanUseCase,
    private val getLoansListUseCase: GetLoansListUseCase,
    private val getLoanByIdUseCase: GetLoanByIdUseCase,
    private val getLoanConditionsUseCase: GetLoanConditionsUseCase,
    private val clearTokenUseCase: ClearTokenUseCase,
    private val token: String
) : ViewModel() {

    private lateinit var navController: NavController

    private var compositeDisposable = CompositeDisposable()

    private val loansList = mutableListOf<Loan>()

    val loadingEvent = SingleLiveEvent<LoadingState>()
    val errorEvent = SingleLiveEvent<ErrorState>()
    val emptyPersonalDataEvent = SingleLiveEvent<Boolean>()
    val emptyAmountEvent = SingleLiveEvent<Boolean>()
    val amountGreaterMaxEvent = SingleLiveEvent<Boolean>()

    val loanCreated = SingleLiveEvent<Boolean>()
    val loansListReceived: MutableLiveData<List<Loan>> = MutableLiveData()
    val loanReceived = SingleLiveEvent<Loan>()
    val conditionsReceived = SingleLiveEvent<Conditions>()

    fun setupNavController(navController: NavController) {
        this.navController = navController
    }

    fun getLoansList() {
        loadingEvent.value = LoadingState.LOADING
        compositeDisposable.add(
            getLoansListUseCase()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { loansListDTO ->
                        loansList.clear()
                        loansList.addAll(loansListDTO.toLoanList())
                        loadingEvent.value = LoadingState.DONE
                        loansListReceived.value = loansListDTO.toLoanList()
                    },
                    { e ->
                        loadingEvent.value = LoadingState.ERROR
                        errorEvent.value = handleNetworkError(e)
                    }
                )
        )
    }

    fun getLoanById(id: Long) {
        loadingEvent.value = LoadingState.LOADING
        compositeDisposable.add(
            getLoanByIdUseCase(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { loanDTO ->
                        loadingEvent.value = LoadingState.DONE
                        loanReceived.value = loanDTO.toLoan()
                    },
                    { e ->
                        loadingEvent.value = LoadingState.ERROR
                        errorEvent.value = handleNetworkError(e)
                    }
                )
        )
    }

    fun getLoansConditions() {
        compositeDisposable.add(
            getLoanConditionsUseCase()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { conditions ->
                        loadingEvent.value = LoadingState.DONE
                        conditionsReceived.value = conditions
                    },
                    { e ->
                        loadingEvent.value = LoadingState.ERROR
                        errorEvent.value = handleNetworkError(e)
                    }
                )
        )
    }

    private fun checkNewLoan(
        amount: String,
        maxAmount: Double,
        fistName: String,
        lastName: String,
        percent: Double,
        period: Int,
        phoneNumber: String
    ) {
        amount.isBlank().let { emptyAmountEvent.value = true }
        amount.toDoubleOrNull()?.let {
            if (it > maxAmount)
                amountGreaterMaxEvent.value = true
            else
                createNewLoan(NewLoanDTO(it, fistName, lastName, percent, period, phoneNumber))
        }

    }

    private fun createNewLoan(newLoanDTO: NewLoanDTO) {
        compositeDisposable.add(
            createLoanUseCase(newLoanDTO)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { loanDTO ->
                        loanCreated.value = true
                        changeFragmentToLoan(loanDTO.id)
                    },
                    { e ->
                        loadingEvent.value = LoadingState.ERROR
                        errorEvent.value = handleNetworkError(e)
                    }
                )
        )
    }

    fun clearTokenAndChangeFragment(action: Int) {
        clearTokenUseCase(AuthFragment.TOKEN_PREFS)
        navController.navigate(action)
    }

    fun changeFragmentToLoan(loanId: Long) {
        navController.navigate(
            R.id.action_loansListFragment_to_loanFragment,
            Bundle().apply {
                putString(AuthFragment.TOKEN_KEY, token)
                putLong(LoansListFragment.LOAN_ID, loanId)
            })
    }

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun showLoanDialog(context: Context) {
        val personalDataDialogLayout =
            LayoutInflater.from(context).inflate(R.layout.get_personal_data_dialog, null)

        AlertDialog
            .Builder(context)
            .setView(personalDataDialogLayout)
            .setPositiveButton(context.getString(R.string.dialog_positive_next_button)) { _: DialogInterface, _: Int ->
                showNewLoanDialog(
                    context,
                    personalDataDialogLayout.first_name.text.trim().toString(),
                    personalDataDialogLayout.last_name.text.trim().toString(),
                    personalDataDialogLayout.phone_number.text.trim().toString()
                )
            }
            .setNegativeButton(context.getString(R.string.dialog_negative_button)) { _: DialogInterface, _: Int -> }
            .create()
            .show()
    }

    private fun showNewLoanDialog(
        context: Context,
        firstName: String,
        lastName: String,
        phoneNumber: String
    ) {
        if (firstName.isBlank() || lastName.isBlank() || phoneNumber.isBlank()) {
            emptyPersonalDataEvent.value = true
            return
        }

        val loanAmountDialogLayout =
            LayoutInflater.from(context).inflate(R.layout.get_loan_amount_dialog, null)

        conditionsReceived.value?.let {
            loanAmountDialogLayout.loan_conditions_info.text =
                context.getString(
                    R.string.dialog_loan_conditions_info,
                    it.maxAmount,
                    it.percent,
                    it.period
                )

            AlertDialog
                .Builder(context)
                .setView(loanAmountDialogLayout)
                .setPositiveButton(context.getString(R.string.dialog_positive_create_button)) { _: DialogInterface, _: Int ->
                    checkNewLoan(
                        loanAmountDialogLayout.loan_amount.text.trim().toString(),
                        it.maxAmount,
                        firstName,
                        lastName,
                        it.percent,
                        it.period,
                        phoneNumber
                    )
                }
                .setNegativeButton(context.getString(R.string.dialog_negative_button)) { _: DialogInterface, _: Int -> }
                .create()
                .show()
        }
    }

    fun filterChanged(loanState: LoanState) {
        when (loanState) {
            LoanState.ALL -> loansListReceived.value = loansList
            LoanState.APPROVED -> loansListReceived.value = loansList.filter {
                it.state == LoanState.APPROVED.name
            }
            LoanState.REGISTERED -> loansListReceived.value = loansList.filter {
                it.state == LoanState.REGISTERED.name
            }
            LoanState.REJECTED -> loansListReceived.value = loansList.filter {
                it.state == LoanState.REJECTED.name
            }
        }
    }

    fun showHelpDialog(context: Context) {
        AlertDialog
            .Builder(context)
            .setView(R.layout.help_dialog)
            .setPositiveButton(context.getString(R.string.dialog_positive_ok_button)) { _: DialogInterface, _: Int -> }
            .create()
            .show()
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}
