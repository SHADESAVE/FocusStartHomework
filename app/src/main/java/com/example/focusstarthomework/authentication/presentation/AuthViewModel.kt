package com.example.focusstarthomework.authentication.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.focusstarthomework.authentication.domain.LoginUseCase
import com.example.focusstarthomework.authentication.domain.RegisterUseCase
import com.example.focusstarthomework.authentication.domain.entity.RegisteredInUser
import com.example.focusstarthomework.authentication.domain.entity.User
import com.example.focusstarthomework.utils.SingleLiveEvent
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import okhttp3.ResponseBody

class AuthViewModel(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private var compositeDisposable = CompositeDisposable()

    val loginEvent = SingleLiveEvent<User>()
    val registerEvent = SingleLiveEvent<User>()
    val loginSuccessful = SingleLiveEvent<String>()

    fun loginClicked(username: String, password: String) {
        loginEvent.value = User(username, password)
    }

    fun registerClicked(username: String, password: String) {
        registerEvent.value = User(username, password)
    }

    fun login(user: User) {
        loginUseCase(user)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<ResponseBody> {
                override fun onSuccess(t: ResponseBody) {
                    loginSuccessful.value = t.string()
                }

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onError(e: Throwable) {
                    Log.e("Login request error", e.message.toString())
                }
            })
    }

    fun register(user: User) {
        registerUseCase(user)
            .subscribe(object : SingleObserver<RegisteredInUser> {
                override fun onSuccess(t: RegisteredInUser) {
                    login(user)
                }

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onError(e: Throwable) {
                    Log.e("Register request error", e.message.toString())
                }
            })
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}