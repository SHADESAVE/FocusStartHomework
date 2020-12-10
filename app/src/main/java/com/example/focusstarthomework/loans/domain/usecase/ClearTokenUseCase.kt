package com.example.focusstarthomework.loans.domain.usecase

import com.example.focusstarthomework.authentication.data.AuthLocalRepositoryImpl

class ClearTokenUseCase(private val localRepository: AuthLocalRepositoryImpl) {

    operator fun invoke(prefsKey: String) =
        localRepository.clearSharedPrefs(prefsKey)
}
