package com.example.focusstarthomework.loan.domain

import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class GetAllLoansUseCase {

    operator fun invoke(storyTextList: List<String>): Flowable<String> {
        return Flowable.fromIterable(storyTextList)
            .concatMap {
                Flowable.just(it)
                    .debounce(700L, TimeUnit.MILLISECONDS)
                    .observeOn(Schedulers.io())
            }
    }
}