package com.example.focusstarthomework.authentication.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.focusstarthomework.TestSchedulerRule
import com.example.focusstarthomework.authentication.data.AuthRemoteDataSourceImpl
import com.example.focusstarthomework.authentication.data.AuthRemoteRepositoryImpl
import com.example.focusstarthomework.authentication.data.network.AuthApi
import com.example.focusstarthomework.authentication.data.network.AuthOkHttpClient
import com.example.focusstarthomework.authentication.data.network.RetrofitBuilder
import com.example.focusstarthomework.authentication.domain.entity.User
import com.example.focusstarthomework.authentication.domain.usecase.GetTokenUseCase
import com.example.focusstarthomework.authentication.domain.usecase.LoginUseCase
import com.example.focusstarthomework.authentication.domain.usecase.RegisterUseCase
import com.example.focusstarthomework.authentication.domain.usecase.SaveTokenUseCase
import com.example.focusstarthomework.utils.ErrorState
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock

class AuthViewModelTest {

    // Для подмены scheduler'ов
    @get:Rule
    var testRule = TestSchedulerRule()

    // Для работы с live data
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val authApi = RetrofitBuilder.create(AuthOkHttpClient.create(), AuthApi::class.java)
    private val remoteDataSource = AuthRemoteDataSourceImpl(authApi)
    private val remoteRepository = AuthRemoteRepositoryImpl(remoteDataSource)

    private val loginUseCase = LoginUseCase(remoteRepository)
    private val registerUseCase = RegisterUseCase(remoteRepository)

    private val saveTokenUseCase = mock(SaveTokenUseCase::class.java)
    private val getTokenUseCase = mock(GetTokenUseCase::class.java)

    private val authViewModel =
        AuthViewModel(loginUseCase, registerUseCase, saveTokenUseCase, getTokenUseCase)

    @Test
    fun `on press login EXPECT triggered loadingEvent`() {
        authViewModel.loginUser(User("ss", "ss"))

        val loadingState = authViewModel.loadingEvent.value

        assertEquals(LoadingState.LOADING, loadingState)
    }

    @Test
    fun `on press login with empty username and password EXPECT triggered emptyFieldsEvent`() {
        authViewModel.loginUser(User("", ""))

        val emptyFieldsState = authViewModel.emptyFieldsEvent.value

        assertEquals(true, emptyFieldsState)
    }

    @Test
    fun `on login http 404 error EXPECT triggered errorEvent`() {
        authViewModel.loginUser(User("string", "qweqwe"))

        val errorState = authViewModel.errorEvent.value

        assertEquals(ErrorState.HTTP404, errorState)
    }

    @Test
    fun `on press register EXPECT triggered loadingEvent`() {
        authViewModel.registerUser(User("ss", "ss"))

        val loadingState = authViewModel.loadingEvent.value

        assertEquals(LoadingState.LOADING, loadingState)
    }

    @Test
    fun `on press register with empty username and password EXPECT triggered emptyFieldsEvent`() {
        authViewModel.registerUser(User("", ""))

        val emptyFieldsState = authViewModel.emptyFieldsEvent.value

        assertEquals(true, emptyFieldsState)
    }

    @Test
    fun `on press register EXPECT triggered emptyFieldsEvent`() {
        authViewModel.registerUser(User("", ""))

        val emptyFieldsState = authViewModel.emptyFieldsEvent.value

        assertEquals(true, emptyFieldsState)
    }

    @Test
    fun `on registration http 404 error EXPECT triggered errorEvent`() {
        authViewModel.registerUser(User("string", "string"))

        val errorState = authViewModel.errorEvent.value

        assertEquals(ErrorState.HTTP404, errorState)
    }
}