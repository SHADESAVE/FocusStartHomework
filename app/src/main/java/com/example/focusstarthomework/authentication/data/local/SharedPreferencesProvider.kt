package com.example.focusstarthomework.authentication.data.local

import android.content.Context

class SharedPreferencesProvider(private val context: Context) {

    fun saveToken(token: String, tokenKey: String, prefsKey: String) {
        context.getSharedPreferences(prefsKey, Context.MODE_PRIVATE).edit()
            .putString(tokenKey, token)
            .apply()
    }

    fun getToken(tokenKey: String, prefKey: String) =
        context.getSharedPreferences(prefKey, Context.MODE_PRIVATE).getString(tokenKey, null)

    fun clear(prefsKey: String) {
        context.getSharedPreferences(prefsKey, Context.MODE_PRIVATE).edit()
            .clear()
            .apply()
    }
}
