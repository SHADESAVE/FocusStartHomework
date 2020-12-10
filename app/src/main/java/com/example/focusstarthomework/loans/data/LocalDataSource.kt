package com.example.focusstarthomework.loans.data

import com.example.focusstarthomework.authentication.data.local.SharedPreferencesProvider

interface LocalDataSource {
    fun saveToken(token: String, tokenKey: String, prefsKey: String)
    fun getToken(tokenKey: String, prefsKey: String): String?
}

class LocalDataSourceImpl(private val sharedPreferencesProvider: SharedPreferencesProvider) :
    LocalDataSource {
    override fun saveToken(token: String, tokenKey: String, prefsKey: String) {
        sharedPreferencesProvider.saveToken(token, tokenKey, prefsKey)
    }

    override fun getToken(tokenKey: String, prefsKey: String) =
        sharedPreferencesProvider.getToken(tokenKey, prefsKey)
}

