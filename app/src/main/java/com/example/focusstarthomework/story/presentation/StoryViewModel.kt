package com.example.focusstarthomework.story.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.focusstarthomework.story.domain.FindMatchingOfTextUseCase
import com.example.focusstarthomework.utils.SingleLiveEvent
import com.example.focusstarthomework.utils.toListOfWords
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class StoryViewModel(
    private val findMatchingOfTextUseCase: FindMatchingOfTextUseCase,
    private val matchString: String
) : ViewModel() {

    val matchText = SingleLiveEvent<String>()
    private val listOfStoryWords = mutableListOf<String>()
    private var disposable: Disposable? = null
    private var matchCounter = 0

    init {
        matchText.value = matchString + matchCounter
    }

    fun setStoryText(storyText: String) {
        listOfStoryWords.clear()
        listOfStoryWords.addAll(storyText.toListOfWords())
    }

    fun searchTextChanged(searchText: CharSequence?) {
        matchCounter = 0
        matchText.value = matchString + matchCounter

        searchText?.let {
            disposable = findMatchingOfTextUseCase(listOfStoryWords)
                .doOnNext { word ->
                    if (it.toString().equals(word, true))
                        matchCounter++
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete {
                    matchText.value = matchString + matchCounter
                }
                .subscribe()
        }
    }

    override fun onCleared() {
        disposable?.dispose()
        disposable = null
        super.onCleared()
    }
}