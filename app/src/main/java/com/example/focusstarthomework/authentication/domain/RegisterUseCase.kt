package com.example.focusstarthomework.authentication.domain

import com.example.focusstarthomework.authentication.data.AuthRepositoryImpl
import com.example.focusstarthomework.authentication.domain.entity.User

class RegisterUseCase(private val authRepository: AuthRepositoryImpl) {

    operator fun invoke(username: String, password: String) =
        authRepository.register(User(name = username, password = password))
}