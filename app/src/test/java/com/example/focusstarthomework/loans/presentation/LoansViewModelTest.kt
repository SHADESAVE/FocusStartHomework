package com.example.focusstarthomework.loans.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.focusstarthomework.TestSchedulerRule
import com.example.focusstarthomework.authentication.data.network.AuthOkHttpClient
import com.example.focusstarthomework.authentication.data.network.RetrofitBuilder
import com.example.focusstarthomework.authentication.presentation.LoadingState
import com.example.focusstarthomework.loans.data.LoansDataSourceImpl
import com.example.focusstarthomework.loans.data.LoansRepositoryImpl
import com.example.focusstarthomework.loans.data.network.LoansApi
import com.example.focusstarthomework.loans.domain.usecase.*
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock

class LoansViewModelTest {

    // Для подмены scheduler'ов
    @get:Rule
    var testRule = TestSchedulerRule()

    // Для работы с live data
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val loansApi = RetrofitBuilder.create(AuthOkHttpClient.create(), LoansApi::class.java)
    private val remoteDataSource = LoansDataSourceImpl(loansApi)
    private val remoteRepository = LoansRepositoryImpl(remoteDataSource)

    private val createLoanUseCase = CreateLoanUseCase(remoteRepository)
    private val getLoansListUseCase = GetLoansListUseCase(remoteRepository)
    private val getLoanByIdUseCase = GetLoanByIdUseCase(remoteRepository)
    private val getLoanConditionsUseCase = GetLoanConditionsUseCase(remoteRepository)
    private val clearTokenUseCase = mock(ClearTokenUseCase::class.java)
    private val token = "String"

    private val loansViewModel =
        LoansViewModel(
            createLoanUseCase,
            getLoansListUseCase,
            getLoanByIdUseCase,
            getLoanConditionsUseCase,
            clearTokenUseCase,
            token
        )

    @Test
    fun `on get loans list EXPECT triggered loadingEvent`() {
        loansViewModel.getLoansList()

        val loadingState = loansViewModel.loadingEvent.value

        Assert.assertEquals(LoadingState.LOADING, loadingState)
    }
}