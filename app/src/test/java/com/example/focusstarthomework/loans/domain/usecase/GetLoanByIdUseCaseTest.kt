package com.example.focusstarthomework.loans.domain.usecase

import com.example.focusstarthomework.authentication.data.network.AuthOkHttpClient
import com.example.focusstarthomework.authentication.data.network.RetrofitBuilder
import com.example.focusstarthomework.loans.data.LoansDataSourceImpl
import com.example.focusstarthomework.loans.data.LoansRepositoryImpl
import com.example.focusstarthomework.loans.data.network.LoansApi
import org.junit.Test
import org.mockito.ArgumentMatchers.anyLong
import retrofit2.HttpException

class GetLoanByIdUseCaseTest {

    private val loansApi = RetrofitBuilder.create(AuthOkHttpClient.create(), LoansApi::class.java)
    private val remoteDataSource = LoansDataSourceImpl(loansApi)
    private val remoteRepository = LoansRepositoryImpl(remoteDataSource)

    @Test
    fun `get loan by id EXPECT http 403 error`() {
        val useCase = GetLoanByIdUseCase(remoteRepository)

        useCase(anyLong())
            .test()
            .await()
            .assertError { e -> e is HttpException && e.code() == 403 }
    }
}