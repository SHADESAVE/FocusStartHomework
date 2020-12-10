package com.example.focusstarthomework.authentication.domain.usecase

import com.example.focusstarthomework.authentication.data.AuthRemoteRepositoryImpl
import com.example.focusstarthomework.authentication.domain.entity.User

class LoginUseCase(private val remoteRepository: AuthRemoteRepositoryImpl) {

    operator fun invoke(user: User) =
        remoteRepository.login(user)
}