package com.example.focusstarthomework.story.domain

import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class FindMatchingOfTextUseCase {

    operator fun invoke(storyTextList: List<String>): Flowable<String> {
        return Flowable.fromIterable(storyTextList)
            .concatMap {
                Flowable.just(it)
                    .debounce(700L, TimeUnit.MILLISECONDS)
                    .observeOn(Schedulers.io())
            }
    }
}