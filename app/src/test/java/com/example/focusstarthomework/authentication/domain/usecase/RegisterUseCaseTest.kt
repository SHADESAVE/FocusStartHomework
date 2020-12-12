package com.example.focusstarthomework.authentication.domain.usecase

import com.example.focusstarthomework.authentication.data.AuthRemoteDataSourceImpl
import com.example.focusstarthomework.authentication.data.AuthRemoteRepositoryImpl
import com.example.focusstarthomework.authentication.data.network.AuthApi
import com.example.focusstarthomework.authentication.data.network.AuthOkHttpClient
import com.example.focusstarthomework.authentication.data.network.RetrofitBuilder
import com.example.focusstarthomework.authentication.domain.entity.User
import org.junit.Test
import retrofit2.HttpException

class RegisterUseCaseTest {

    private val authApi = RetrofitBuilder.create(AuthOkHttpClient.create(), AuthApi::class.java)
    private val remoteDataSource = AuthRemoteDataSourceImpl(authApi)
    private val remoteRepository = AuthRemoteRepositoryImpl(remoteDataSource)

    // one-time test
    @Test
    fun `register new user EXPECT complete`() {
        val useCase = RegisterUseCase(remoteRepository)

        useCase(User("string999", "string999"))
            .test()
            .await()
            .assertComplete()
    }

    @Test
    fun `register already registered user EXPECT http 400 error`() {
        val useCase = RegisterUseCase(remoteRepository)

        useCase(User("string", "string"))
            .test()
            .await()
            .assertError { t -> t is HttpException && t.code() == 400 }
    }
}