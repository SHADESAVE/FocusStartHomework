package com.example.focusstarthomework.authentication.data

import com.example.focusstarthomework.authentication.domain.entity.RegisteredInUser
import com.example.focusstarthomework.authentication.domain.entity.User
import io.reactivex.Single
import okhttp3.ResponseBody

interface AuthDataSource {
    fun login(user: User): Single<ResponseBody>
    fun register(user: User): Single<RegisteredInUser>
}

class AuthDataSourceImpl(private val authApi: AuthApi) : AuthDataSource {
    override fun login(user: User) =
        authApi.login(user)

    override fun register(user: User) =
        authApi.register(user)
}

