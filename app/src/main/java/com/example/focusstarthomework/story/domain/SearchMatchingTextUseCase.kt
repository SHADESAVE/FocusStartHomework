package com.example.focusstarthomework.story.domain

import com.example.focusstarthomework.utils.SingleLiveEvent
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SearchMatchingTextUseCase(storyText: String) {

    private val storyTextList = storyText
        .split("\n", ",", " ", ".", "—", ":", "!", "?")
        .filter { it.isNotEmpty() }

    private var matchCounter = 0

    operator fun invoke(matchText: SingleLiveEvent<String>, searchWord: String): Disposable {
        matchCounter = 0
        matchText.value = "Количество совпадений: $matchCounter"

        return Flowable.fromIterable(storyTextList)
            .concatMap {
                Flowable.just(it)
                    .debounce(700L, TimeUnit.MILLISECONDS)
                    .observeOn(Schedulers.io())
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { word ->
                if (searchWord.equals(word, true)) {
                    matchText.value = "Количество совпадений: ${++matchCounter}"
                }
            }
    }
}