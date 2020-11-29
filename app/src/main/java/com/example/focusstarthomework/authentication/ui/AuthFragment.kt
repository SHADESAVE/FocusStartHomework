package com.example.focusstarthomework.authentication.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
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

    private var sharedPrefs: SharedPreferences? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loadToken()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loginSuccessful.observe(viewLifecycleOwner, Observer {
            saveToken(it)
            findNavController().navigate(R.id.action_authFragment_to_loanFragment)
        })

        viewModel.loginEvent.observe(viewLifecycleOwner, Observer {
            viewModel.login(it)
        })

        viewModel.registerEvent.observe(viewLifecycleOwner, Observer {
            viewModel.register(it)
        })

        login_button.setOnClickListener {
            viewModel.loginClicked(username.text.toString().trim(), password.text.toString().trim())
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

        Toast.makeText(context, "Data saved", Toast.LENGTH_SHORT).show()
    }

    private fun loadToken() {
        val sharedPreferences =
            activity?.getSharedPreferences(TOKEN_PREFS, Context.MODE_PRIVATE) ?: return
        val token = sharedPreferences.getString(TOKEN_KEY, null)

        Toast.makeText(context, "Data loaded", Toast.LENGTH_SHORT).show()

        token?.let {
            findNavController().navigate(R.id.action_authFragment_to_loanFragment)
        }
    }
}