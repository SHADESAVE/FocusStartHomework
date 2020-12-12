package com.example.focusstarthomework.authentication.domain.usecase

import com.example.focusstarthomework.authentication.data.AuthRemoteDataSourceImpl
import com.example.focusstarthomework.authentication.data.AuthRemoteRepositoryImpl
import com.example.focusstarthomework.authentication.data.network.AuthApi
import com.example.focusstarthomework.authentication.data.network.AuthOkHttpClient
import com.example.focusstarthomework.authentication.data.network.RetrofitBuilder
import com.example.focusstarthomework.authentication.domain.entity.User
import org.junit.Test
import retrofit2.HttpException

class LoginUseCaseTest {

    private val authApi = RetrofitBuilder.create(AuthOkHttpClient.create(), AuthApi::class.java)
    private val remoteDataSource = AuthRemoteDataSourceImpl(authApi)
    private val remoteRepository = AuthRemoteRepositoryImpl(remoteDataSource)

    @Test
    fun `login correct username and password EXPECT complete`() {
        val useCase = LoginUseCase(remoteRepository)

        useCase(User("string", "string"))
            .test()
            .await()
            .assertComplete()
    }

    @Test
    fun `login wrong username or password EXPECT http 404 error`() {
        val useCase = LoginUseCase(remoteRepository)

        useCase(User("string", "wrongpassword"))
            .test()
            .await()
            .assertError { e -> e is HttpException && e.code() == 404 }
    }
}