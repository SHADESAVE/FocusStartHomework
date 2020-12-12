package com.example.focusstarthomework.authentication.domain.usecase

import androidx.test.platform.app.InstrumentationRegistry
import com.example.focusstarthomework.authentication.data.AuthLocalDataSourceImpl
import com.example.focusstarthomework.authentication.data.AuthLocalRepositoryImpl
import com.example.focusstarthomework.authentication.data.local.SharedPreferencesProvider
import com.example.focusstarthomework.authentication.ui.AuthFragment
import org.junit.Assert.assertEquals
import org.junit.Test

class SaveTokenUseCaseTest {

    private val localDataSource = AuthLocalDataSourceImpl(
        SharedPreferencesProvider(InstrumentationRegistry.getInstrumentation().context)
    )
    private val localRepository = AuthLocalRepositoryImpl(localDataSource)

    @Test
    fun saveToken() {
        val saveTokenUseCase = SaveTokenUseCase(localRepository)
        saveTokenUseCase("test-token", AuthFragment.TOKEN_KEY, AuthFragment.TOKEN_PREFS)
        val getTokenUseCase = GetTokenUseCase(localRepository)

        val actual = getTokenUseCase(AuthFragment.TOKEN_KEY, AuthFragment.TOKEN_PREFS)

        val expected = "test-token"
        assertEquals(expected, actual)
    }
}