package com.example.focusstarthomework.story.presentation

import androidx.lifecycle.ViewModel
import com.example.focusstarthomework.story.domain.SearchMatchingTextUseCase
import com.example.focusstarthomework.utils.SingleLiveEvent
import io.reactivex.disposables.Disposable

class StoryViewModel(
    private val searchMatchingTextUseCase: SearchMatchingTextUseCase
) : ViewModel() {

    val matchText = SingleLiveEvent<String>()
    private var disposable: Disposable? = null

    fun searchTextChanged(searchText: CharSequence?) {
        searchText?.let {
            disposable = searchMatchingTextUseCase(matchText, it.toString())
        }
    }

    override fun onCleared() {
        disposable?.dispose()
        disposable = null
        super.onCleared()
    }
}