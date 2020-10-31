package com.example.focusstarthomework.speedometer.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.focusstarthomework.R
import kotlinx.android.synthetic.main.fragment_speedometer.*

class SpeedometerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_speedometer, container, false)
    }
}