package com.example.focusstarthomework.authentication.data

import com.example.focusstarthomework.authentication.domain.AuthRepository
import com.example.focusstarthomework.authentication.domain.entity.User

class AuthRepositoryImpl(
    private val authDataSource: AuthDataSourceImpl
): AuthRepository {
    override fun login(user: User) =
        authDataSource.login(user)

    override fun register(user: User) =
        authDataSource.register(user)
}