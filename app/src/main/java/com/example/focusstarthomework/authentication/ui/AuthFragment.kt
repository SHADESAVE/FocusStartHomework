package com.example.focusstarthomework.authentication.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.focusstarthomework.R
import com.example.focusstarthomework.authentication.di.AuthViewModelFactory
import com.example.focusstarthomework.authentication.presentation.AuthViewModel
import kotlinx.android.synthetic.main.fragment_login.*

class AuthFragment : Fragment(R.layout.fragment_login) {

    companion object {
        const val TOKEN_PREFS = "TOKEN_PREFS"
        const val TOKEN_KEY = "TOKEN_KEY"
    }

    private val viewModel: AuthViewModel by viewModels {
        AuthViewModelFactory()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadToken()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loginEvent.observe(viewLifecycleOwner, Observer { viewModel.login(it) })
        viewModel.registerEvent.observe(viewLifecycleOwner, Observer { viewModel.register(it) })
        viewModel.loginSuccessful.observe(viewLifecycleOwner, Observer {
            saveToken(it)
            changeFragment(it)
        })

        login_button.setOnClickListener {
            viewModel.loginClicked(
                username.text.toString().trim(),
                password.text.toString().trim()
            )
        }
        register_button.setOnClickListener {
            viewModel.registerClicked(
                username.text.toString().trim(),
                password.text.toString().trim()
            )
        }
    }

    private fun saveToken(token: String) {
        val sharedPreferences =
            activity?.getSharedPreferences(TOKEN_PREFS, Context.MODE_PRIVATE) ?: return
        val editor = sharedPreferences.edit()
        editor.apply {
            putString(TOKEN_KEY, token)
        }.apply()
    }

    private fun loadToken() {
        val sharedPreferences =
            activity?.getSharedPreferences(TOKEN_PREFS, Context.MODE_PRIVATE) ?: return
        val token = sharedPreferences.getString(TOKEN_KEY, null)
        if (!token.isNullOrBlank()) {
            changeFragment(token)
        }
    }

    private fun changeFragment(token: String) {
        findNavController().navigate(
            R.id.action_authFragment_to_loansListFragment,
            Bundle().apply { putString(TOKEN_KEY, token) })
    }
}