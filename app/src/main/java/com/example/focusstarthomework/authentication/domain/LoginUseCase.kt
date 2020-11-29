package com.example.focusstarthomework.authentication.domain

import com.example.focusstarthomework.authentication.data.AuthRepositoryImpl
import com.example.focusstarthomework.authentication.domain.entity.User

class LoginUseCase(private val authRepository: AuthRepositoryImpl) {

    operator fun invoke(username: String, password: String) =
        authRepository.login(User(name = username, password = password))
}