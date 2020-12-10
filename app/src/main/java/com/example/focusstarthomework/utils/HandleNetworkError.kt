package com.example.focusstarthomework.utils

import android.util.Log
import retrofit2.HttpException
import java.net.UnknownHostException

enum class ErrorState {
    UNKNOWN_HOST,
    UNHANDLED_ERROR,
    HTTP401,
    HTTP403,
    HTTP404
}

fun handleNetworkError(error: Throwable): ErrorState {
    Log.e("authErr", error.toString())
    return when (error) {
        is HttpException -> {
            when (error.code()) {
                401 -> ErrorState.HTTP401
                403 -> ErrorState.HTTP403
                404 -> ErrorState.HTTP404
                else -> ErrorState.UNHANDLED_ERROR
            }
        }
        is UnknownHostException -> {
            ErrorState.UNKNOWN_HOST
        }
        else -> {
            ErrorState.UNHANDLED_ERROR
        }
    }
}