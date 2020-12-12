package com.example.focusstarthomework.loans.domain.usecase

import com.example.focusstarthomework.authentication.data.network.AuthOkHttpClient
import com.example.focusstarthomework.authentication.data.network.RetrofitBuilder
import com.example.focusstarthomework.loans.data.LoansDataSourceImpl
import com.example.focusstarthomework.loans.data.LoansRepositoryImpl
import com.example.focusstarthomework.loans.data.network.LoansApi
import com.example.focusstarthomework.loans.domain.entity.NewLoanDTO
import org.junit.Test
import retrofit2.HttpException

class CreateLoanUseCaseTest {

    private val loansApi = RetrofitBuilder.create(AuthOkHttpClient.create(), LoansApi::class.java)
    private val remoteDataSource = LoansDataSourceImpl(loansApi)
    private val remoteRepository = LoansRepositoryImpl(remoteDataSource)

    @Test
    fun `create new loan EXPECT http 403 error`() {
        val useCase = CreateLoanUseCase(remoteRepository)
        val newLoan = NewLoanDTO(
            25000.0,
            "Pavel",
            "Pavel",
            7.5,
            12,
            "+7 000 000-00-00"
        )

        useCase(newLoan)
            .test()
            .await()
            .assertError { e -> e is HttpException && e.code() == 403 }
    }
}