package com.example.focusstarthomework.authentication.data

import com.example.focusstarthomework.authentication.data.local.SharedPreferencesProvider

interface AuthLocalDataSource {
    fun saveToken(token: String, tokenKey: String, prefsKey: String)
    fun getToken(tokenKey: String, prefsKey: String): String?
    fun clearSharedPrefs(prefsKey: String)
}

class AuthLocalDataSourceImpl(
    private val sharedPreferencesProvider: SharedPreferencesProvider
) : AuthLocalDataSource {

    override fun saveToken(token: String, tokenKey: String, prefsKey: String) {
        sharedPreferencesProvider.saveToken(token, tokenKey, prefsKey)
    }

    override fun getToken(tokenKey: String, prefsKey: String) =
        sharedPreferencesProvider.getToken(tokenKey, prefsKey)

    override fun clearSharedPrefs(prefsKey: String) {
        sharedPreferencesProvider.clear(prefsKey)
    }
}

