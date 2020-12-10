package com.example.focusstarthomework.loans.data.network

import com.example.focusstarthomework.loans.domain.entity.Conditions
import com.example.focusstarthomework.loans.domain.entity.LoanDTO
import com.example.focusstarthomework.loans.domain.entity.NewLoanDTO
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface LoansApi {

    @GET("/loans/all")
    fun getLoansList(): Single<List<LoanDTO>>

    @GET("loans/{id}")
    fun getLoanById(@Path("id") id: Long): Single<LoanDTO>

    @GET("/loans/conditions")
    fun getLoanConditions(): Single<Conditions>

    @POST("/loans")
    fun createNewLoan(@Body newLoanDTO: NewLoanDTO): Single<LoanDTO>

}