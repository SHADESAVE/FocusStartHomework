package com.example.focusstarthomework.authentication.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.focusstarthomework.R
import com.example.focusstarthomework.authentication.di.AuthViewModelFactory
import com.example.focusstarthomework.authentication.domain.entity.User
import com.example.focusstarthomework.authentication.presentation.AuthViewModel
import com.example.focusstarthomework.authentication.presentation.LoadingState
import com.example.focusstarthomework.utils.ErrorState
import kotlinx.android.synthetic.main.fragment_login.*

class AuthFragment : Fragment(R.layout.fragment_login) {

    companion object {
        const val TOKEN_PREFS = "TOKEN_PREFS"
        const val TOKEN_KEY = "TOKEN_KEY"
        const val NEW_USER = "NEW_USER"
    }

    private val viewModel: AuthViewModel by viewModels {
        AuthViewModelFactory(requireContext())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.checkToken()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadingEvent.observe(viewLifecycleOwner, Observer(::loadingManager))
        viewModel.errorEvent.observe(viewLifecycleOwner, Observer(::showError))
        viewModel.emptyFieldsEvent.observe(viewLifecycleOwner, Observer {
            viewModel.showToast(requireContext(), getString(R.string.empty_auth))
        })
        viewModel.setupNavController(findNavController())

        setupButtonClickListeners()
    }

    private fun setupButtonClickListeners() {
        login_button.setOnClickListener {
            viewModel.loginUser(
                User(username.text.toString().trim(), password.text.toString().trim())
            )
        }

        register_button.setOnClickListener {
            viewModel.registerUser(
                User(username.text.toString().trim(), password.text.toString().trim())
            )
        }
    }

    private fun loadingManager(loadingState: LoadingState) {
        when (loadingState) {
            LoadingState.LOADING -> {
                loading.visibility = View.VISIBLE
                username.visibility = View.GONE
                password.visibility = View.GONE
                login_button.visibility = View.GONE
                register_button.visibility = View.GONE
            }
            LoadingState.DONE -> {
                loading.visibility = View.GONE
                username.visibility = View.VISIBLE
                password.visibility = View.VISIBLE
                login_button.visibility = View.VISIBLE
                register_button.visibility = View.VISIBLE
            }
            LoadingState.ERROR -> {
                loading.visibility = View.GONE
                username.visibility = View.VISIBLE
                password.visibility = View.VISIBLE
                login_button.visibility = View.VISIBLE
                register_button.visibility = View.VISIBLE
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

}