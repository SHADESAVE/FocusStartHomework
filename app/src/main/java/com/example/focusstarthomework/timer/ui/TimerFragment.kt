package com.example.focusstarthomework.timer.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.work.*
import com.example.focusstarthomework.R
import com.example.focusstarthomework.utils.checkBattery
import com.example.focusstarthomework.utils.makeStatusNotification
import com.example.focusstarthomework.utils.toTimeFormatString
import kotlinx.android.synthetic.main.fragment_timer.*
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit

const val MIN_TIME = 0
const val MAX_TIME = 60
const val TIMER_TIME = "TIMER_TIME"
const val TIMER_TAG = "TIMER_TAG"

class TimerFragment : Fragment() {

    private val workManager by lazy { WorkManager.getInstance(requireContext()) }
    private var timerJob: Job? = null

    private var overallTimeInMilliseconds = 0L
    private var timerState = TimerState.STOP

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_timer, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        workManager.cancelAllWorkByTag(TIMER_TAG)
        checkBattery(requireContext())

        setupNumberPickers()
        setupPlayButtonSwitcher()
        setupRefreshButton()
    }

    override fun onStop() {
        super.onStop()
        startTimerWorker()
    }

    override fun onResume() {
        super.onResume()
        workManager.cancelAllWorkByTag(TIMER_TAG)
    }

    private fun startTimerWorker() {
        if (timerState == TimerState.START || timerState == TimerState.RESUME) {
            workManager.enqueue(
                OneTimeWorkRequest.Builder(TimerWorker::class.java)
//                    .setInitialDelay(overallTimeInMilliseconds, TimeUnit.MILLISECONDS)
                    .setInputData(
                        Data.Builder().putLong(TIMER_TIME, overallTimeInMilliseconds).build()
                    )
                    .addTag(TIMER_TAG)
                    .build()
            )
        }
    }

    private fun setupNumberPickers() {
        hours_picker.minValue = MIN_TIME
        hours_picker.maxValue = MAX_TIME
        minutes_picker.minValue = MIN_TIME
        minutes_picker.maxValue = MAX_TIME
        seconds_picker.minValue = MIN_TIME
        seconds_picker.maxValue = MAX_TIME
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

    private fun setupRefreshButton() {
        refresh_image_button.setOnClickListener {
            it.visibility = View.GONE

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

            timer_finish_text.visibility = View.GONE
            play_button_switcher.visibility = View.VISIBLE
        }
    }


    private fun changeState(timerState: TimerState) {
        this.timerState = timerState

        when (this.timerState) {
            TimerState.START -> {
                overallTimeInMilliseconds = getTimeInMilliseconds()
                startTimerJob()
            }
            TimerState.PAUSE -> timerJob?.cancel()
            TimerState.RESUME -> startTimerJob()
            TimerState.STOP -> {
                timerJob?.cancel()
                updateViewAfterTimerStopped()
            }
        }
    }

    private fun updateViewAfterTimerStopped() {
        overallTimeInMilliseconds = 0L
        timer_text.text = overallTimeInMilliseconds.toTimeFormatString()
        timer_finish_text.visibility = View.VISIBLE
        play_button_switcher.visibility = View.GONE
    }


    private fun startTimerJob() {
        timerJob = CoroutineScope(Dispatchers.Main).launch {
            try {
                while (overallTimeInMilliseconds > 0) {
                    timer_text.text = overallTimeInMilliseconds.toTimeFormatString()
                    delay(1000L)
                    overallTimeInMilliseconds -= 1000L
                }
                changeState(TimerState.STOP)
                makeStatusNotification("Время истекло", requireContext())

            } catch (e: Throwable) {
                Log.d("timeJobCatch", e.toString())
            }
        }
    }

    private fun getTimeInMilliseconds(): Long {
        val millisecondsInHour = 3600000L
        val millisecondsInMinute = 60000L
        val millisecondsInSecond = 1000L
        return hours_picker.value * millisecondsInHour + minutes_picker.value * millisecondsInMinute + seconds_picker.value * millisecondsInSecond
    }
}