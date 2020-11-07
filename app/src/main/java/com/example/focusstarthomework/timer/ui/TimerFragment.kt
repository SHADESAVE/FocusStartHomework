package com.example.focusstarthomework.timer.ui

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.example.focusstarthomework.R
import com.example.focusstarthomework.utils.checkBattery
import com.example.focusstarthomework.utils.toTimeFormatString
import kotlinx.android.synthetic.main.fragment_timer.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

const val MIN_TIME = 0
const val MAX_TIME = 60
const val TICK_ERROR = 999L

class TimerFragment : Fragment() {

    private var overallTimeInMilliseconds = 0L
    private var timerState = TimerState.STOP
    private var timer: CountDownTimer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_timer, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkBattery(requireContext())

        setupNumberPickers()
        setupPlayButtonSwitcher()
        setupRefreshButton()
    }

    override fun onStop() {
        super.onStop()

        if (timerState == TimerState.START || timerState == TimerState.RESUME) {
            val uploadWorkRequest: WorkRequest =
                OneTimeWorkRequestBuilder<TimerWorker>().setInitialDelay(overallTimeInMilliseconds, TimeUnit.MILLISECONDS).build()
            WorkManager.getInstance(requireContext()).enqueue(uploadWorkRequest)

        }
    }

    private fun setupRefreshButton() {
        refresh_image_button.setOnClickListener {
            it.visibility = View.GONE
            timer_finish_text.visibility = View.GONE

            play_button_switcher.visibility = View.VISIBLE
            hours_picker.visibility = View.VISIBLE
            minutes_picker.visibility = View.VISIBLE
            seconds_picker.visibility = View.VISIBLE

            if (play_button_switcher.currentView !== play_image)
                play_button_switcher.showNext()

            timer_text.text = getString(R.string.default_time)

            hours_picker.value = MIN_TIME
            minutes_picker.value = MIN_TIME
            seconds_picker.value = MIN_TIME

            changeState(TimerState.STOP)
        }
    }

    private fun setupPlayButtonSwitcher() {
        play_button_switcher.setOnClickListener {
            if (hours_picker.value > 0 || minutes_picker.value > 0 || seconds_picker.value > 0) {
                refresh_image_button.visibility = View.VISIBLE
                hours_picker.visibility = View.GONE
                minutes_picker.visibility = View.GONE
                seconds_picker.visibility = View.GONE

                when (play_button_switcher.currentView) {
                    play_image ->
                        if (timerState == TimerState.STOP)
                            changeState(TimerState.START)
                        else
                            changeState(TimerState.RESUME)
                    pause_image -> changeState(TimerState.PAUSE)
                }
                play_button_switcher.showNext()
            }
        }
    }

    private fun changeState(timerState: TimerState) {
        this.timerState = timerState

        when (this.timerState) {
            TimerState.START -> {
                overallTimeInMilliseconds = getTimeInMilliseconds()
                startTimer()
            }
            TimerState.PAUSE -> timer?.cancel()
            TimerState.RESUME -> {
                startTimer()
            }
            TimerState.STOP -> timer?.cancel()
        }
    }

    private fun startTimer() {
        timer = initTimer(overallTimeInMilliseconds)
        GlobalScope.launch {
            delay(500L)
            Log.d("timerTimeInS", overallTimeInMilliseconds.toString())
            timer?.start()
        }
    }

    private fun updateTimerText() {
        timer_text.text = overallTimeInMilliseconds.toTimeFormatString()
    }

    private fun initTimer(timeInMilliSeconds: Long): CountDownTimer =
        object : CountDownTimer(timeInMilliSeconds, 1000) {

            override fun onFinish() {
                overallTimeInMilliseconds = 0
                timer_finish_text.visibility = View.VISIBLE
                play_button_switcher.visibility = View.GONE

                val uploadWorkRequest: WorkRequest =
                    OneTimeWorkRequestBuilder<TimerWorker>().build()
                WorkManager.getInstance(requireContext()).enqueue(uploadWorkRequest)

            }

            override fun onTick(timeInMilliSeconds: Long) {
                overallTimeInMilliseconds = timeInMilliSeconds
                Log.d("timerTick", timeInMilliSeconds.toString())
                updateTimerText()
            }
        }

    private fun getTimeInMilliseconds(): Long =
        (hours_picker.value * 360 + minutes_picker.value * 60 + seconds_picker.value) * 1000.toLong() + TICK_ERROR

    private fun setupNumberPickers() {
        hours_picker.minValue = MIN_TIME
        hours_picker.maxValue = MAX_TIME
        minutes_picker.minValue = MIN_TIME
        minutes_picker.maxValue = MAX_TIME
        seconds_picker.minValue = MIN_TIME
        seconds_picker.maxValue = MAX_TIME
    }
}