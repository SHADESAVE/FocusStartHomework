package com.example.focusstarthomework.loans.domain.usecase

import com.example.focusstarthomework.authentication.data.network.AuthOkHttpClient
import com.example.focusstarthomework.authentication.data.network.RetrofitBuilder
import com.example.focusstarthomework.loans.data.LoansDataSourceImpl
import com.example.focusstarthomework.loans.data.LoansRepositoryImpl
import com.example.focusstarthomework.loans.data.network.LoansApi
import org.junit.Test
import retrofit2.HttpException

class GetLoanConditionsUseCaseTest {

    private val loansApi = RetrofitBuilder.create(AuthOkHttpClient.create(), LoansApi::class.java)
    private val remoteDataSource = LoansDataSourceImpl(loansApi)
    private val remoteRepository = LoansRepositoryImpl(remoteDataSource)

    @Test
    fun `get loan conditions EXPECT http 403 error`() {
        val useCase = GetLoanConditionsUseCase(remoteRepository)

        useCase()
            .test()
            .await()
            .assertError { e -> e is HttpException && e.code() == 403 }
    }
}