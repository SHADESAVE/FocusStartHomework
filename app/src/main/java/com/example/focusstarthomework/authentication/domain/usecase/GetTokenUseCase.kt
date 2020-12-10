package com.example.focusstarthomework.authentication.domain.usecase

import com.example.focusstarthomework.authentication.data.AuthLocalRepositoryImpl

class GetTokenUseCase(private val localRepository: AuthLocalRepositoryImpl) {

    operator fun invoke(tokenKey: String, prefsKey: String) =
        localRepository.getToken(tokenKey, prefsKey)

}