package com.example.focusstarthomework.authentication.domain

interface AuthLocalRepository {
    fun saveToken(token: String, tokenKey: String, prefsKey: String)
    fun getToken(tokenKey: String, prefsKey: String): String?
    fun clearSharedPrefs(prefsKey: String)
}