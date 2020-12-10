package com.example.focusstarthomework.authentication.data

import com.example.focusstarthomework.authentication.data.network.AuthApi
import com.example.focusstarthomework.authentication.domain.entity.RegisteredInUser
import com.example.focusstarthomework.authentication.domain.entity.User
import io.reactivex.Single
import okhttp3.ResponseBody

interface AuthRemoteDataSource {
    fun login(user: User): Single<ResponseBody>
    fun register(user: User): Single<RegisteredInUser>
}

class AuthRemoteDataSourceImpl(private val authApi: AuthApi) : AuthRemoteDataSource {

    override fun login(user: User) =
        authApi.login(user)

    override fun register(user: User) =
        authApi.register(user)
}

