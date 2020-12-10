package com.example.focusstarthomework.authentication.domain.usecase

import com.example.focusstarthomework.authentication.data.AuthLocalRepositoryImpl

class SaveTokenUseCase(private val localRepository: AuthLocalRepositoryImpl) {

    operator fun invoke(token: String, tokenKey: String, prefsKey: String) {
        localRepository.saveToken(token, tokenKey, prefsKey)
    }
}