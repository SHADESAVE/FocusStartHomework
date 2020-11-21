package com.example.focusstarthomework.story.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.focusstarthomework.R
import com.example.focusstarthomework.story.di.StoryViewModelFactory
import com.example.focusstarthomework.story.presentation.StoryViewModel
import kotlinx.android.synthetic.main.fragment_story.*

class StoryFragment : Fragment(R.layout.fragment_story) {

    private val viewModel: StoryViewModel by viewModels {
        StoryViewModelFactory(getString(R.string.story_text))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.matchText.observe(viewLifecycleOwner, Observer {
            match_text.text = it
        })

        search_edit_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int)  {}

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.searchTextChanged(text)
            }
        })
    }
}