package com.example.focusstarthomework.authentication.data

import com.example.focusstarthomework.authentication.domain.AuthLocalRepository

class AuthLocalRepositoryImpl(
    private val localDataSource: AuthLocalDataSource
) : AuthLocalRepository {

    override fun saveToken(token: String, tokenKey: String, prefsKey: String) {
        localDataSource.saveToken(token, tokenKey, prefsKey)
    }

    override fun getToken(tokenKey: String, prefsKey: String): String? =
        localDataSource.getToken(tokenKey, prefsKey)

    override fun clearSharedPrefs(prefsKey: String) {
        localDataSource.clearSharedPrefs(prefsKey)
    }
}