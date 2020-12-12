package com.example.focusstarthomework.loans.domain.usecase

import androidx.test.platform.app.InstrumentationRegistry
import com.example.focusstarthomework.authentication.data.AuthLocalDataSourceImpl
import com.example.focusstarthomework.authentication.data.AuthLocalRepositoryImpl
import com.example.focusstarthomework.authentication.data.local.SharedPreferencesProvider
import com.example.focusstarthomework.authentication.domain.usecase.GetTokenUseCase
import com.example.focusstarthomework.authentication.domain.usecase.SaveTokenUseCase
import com.example.focusstarthomework.authentication.ui.AuthFragment
import org.junit.Assert.assertEquals
import org.junit.Test

class ClearTokenUseCaseTest {

    private val localDataSource = AuthLocalDataSourceImpl(
        SharedPreferencesProvider(InstrumentationRegistry.getInstrumentation().context)
    )
    private val localRepository = AuthLocalRepositoryImpl(localDataSource)

    @Test
    fun getNullToken() {
        val saveTokenUseCase = SaveTokenUseCase(localRepository)
        saveTokenUseCase("test-token", AuthFragment.TOKEN_KEY, AuthFragment.TOKEN_PREFS)
        val getTokenUseCase = GetTokenUseCase(localRepository)
        val clearTokenUseCase = ClearTokenUseCase(localRepository)
        clearTokenUseCase(AuthFragment.TOKEN_PREFS)

        val actual = getTokenUseCase(AuthFragment.TOKEN_KEY, AuthFragment.TOKEN_PREFS)

        assertEquals(null, actual)
    }
}