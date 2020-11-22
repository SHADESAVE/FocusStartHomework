package com.example.focusstarthomework.story.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.focusstarthomework.story.domain.FindMatchingOfTextUseCase
import com.example.focusstarthomework.story.presentation.StoryViewModel

class StoryViewModelFactory(private val matchText: String) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass == StoryViewModel::class.java) {

            val uploadImageUseCase = FindMatchingOfTextUseCase()

            return StoryViewModel(uploadImageUseCase, matchText) as T
        } else {
            error("Unexpected class $modelClass")
        }
    }
}