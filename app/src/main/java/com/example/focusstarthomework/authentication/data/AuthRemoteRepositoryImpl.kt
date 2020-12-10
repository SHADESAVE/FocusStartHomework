package com.example.focusstarthomework.authentication.data

import com.example.focusstarthomework.authentication.domain.AuthRemoteRepository
import com.example.focusstarthomework.authentication.domain.entity.User

class AuthRemoteRepositoryImpl(
    private val remoteDataSource: AuthRemoteDataSource
) : AuthRemoteRepository {

    override fun login(user: User) =
        remoteDataSource.login(user)

    override fun register(user: User) =
        remoteDataSource.register(user)
}