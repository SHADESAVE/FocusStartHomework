package com.example.focusstarthomework.authentication.domain

import com.example.focusstarthomework.authentication.domain.entity.RegisteredInUser
import com.example.focusstarthomework.authentication.domain.entity.User
import io.reactivex.Single
import okhttp3.ResponseBody

interface AuthRemoteRepository {
    fun login(user: User): Single<ResponseBody>
    fun register(user: User): Single<RegisteredInUser>
}