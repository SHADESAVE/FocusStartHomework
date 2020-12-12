package com.example.focusstarthomework.authentication.presentation

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.focusstarthomework.R
import com.example.focusstarthomework.authentication.domain.entity.User
import com.example.focusstarthomework.authentication.domain.usecase.GetTokenUseCase
import com.example.focusstarthomework.authentication.domain.usecase.LoginUseCase
import com.example.focusstarthomework.authentication.domain.usecase.RegisterUseCase
import com.example.focusstarthomework.authentication.domain.usecase.SaveTokenUseCase
import com.example.focusstarthomework.authentication.ui.AuthFragment
import com.example.focusstarthomework.utils.ErrorState
import com.example.focusstarthomework.utils.SingleLiveEvent
import com.example.focusstarthomework.utils.handleNetworkError
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class AuthViewModel(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val saveTokenUseCase: SaveTokenUseCase,
    private val getTokenUseCase: GetTokenUseCase
) : ViewModel() {

    private lateinit var navController: NavController

    private var compositeDisposable = CompositeDisposable()
    private var newUser = false

    val loginEvent = SingleLiveEvent<User>()
    val registerEvent = SingleLiveEvent<User>()
    val loadingEvent = SingleLiveEvent<LoadingState>()
    val errorEvent = SingleLiveEvent<ErrorState>()
    val emptyFieldsEvent = SingleLiveEvent<Boolean>()

    fun setupNavController(navController: NavController) {
        this.navController = navController
    }

    fun checkToken() {
        val token = getTokenUseCase(AuthFragment.TOKEN_KEY, AuthFragment.TOKEN_PREFS)
        if (!token.isNullOrEmpty())
            changeFragmentToLoansList(token)
    }

    fun loginClicked(username: String, password: String) {
        if (username.isBlank() || password.isBlank()) {
            emptyFieldsEvent.value = true
            return
        }
        loginEvent.value = User(username, password)
    }

    fun registerClicked(username: String, password: String) {
        if (username.isBlank() || password.isBlank()) {
            emptyFieldsEvent.value = true
            return
        }
        registerEvent.value = User(username, password)
    }

    fun loginUser(user: User) {
        loadingEvent.value = LoadingState.LOADING
        compositeDisposable.add(
            loginUseCase(user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { response ->
                        saveToken(response.string())
                        loadingEvent.value = LoadingState.DONE
                    },
                    { e ->
                        loadingEvent.value = LoadingState.ERROR
                        errorEvent.value = handleNetworkError(e)
                    }
                )
        )
    }

    fun registerUser(user: User) {
        loadingEvent.value = LoadingState.LOADING
        compositeDisposable.addAll(
            registerUseCase(user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { _ ->
                        newUser = true
                        loginUser(user)
                    },
                    { e ->
                        loadingEvent.value = LoadingState.ERROR
                        errorEvent.value = handleNetworkError(e)
                    }
                )
        )
    }

    private fun saveToken(token: String) {
        saveTokenUseCase(token, AuthFragment.TOKEN_KEY, AuthFragment.TOKEN_PREFS)
        changeFragmentToLoansList(token)
    }

    private fun changeFragmentToLoansList(token: String) {
        navController.navigate(
            R.id.action_authFragment_to_loansListFragment,
            Bundle().apply {
                putString(AuthFragment.TOKEN_KEY, token)
                putBoolean(AuthFragment.NEW_USER, newUser)
            })
    }

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}