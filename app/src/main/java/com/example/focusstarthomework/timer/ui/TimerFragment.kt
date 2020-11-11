package com.example.focusstarthomework.timer.ui

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
import com.example.focusstarthomework.utils.toTimeFormatString
import kotlinx.android.synthetic.main.fragment_timer.*

const val MIN_TIME = 0
const val MAX_TIME = 60
const val TIMER_TIME = "TIMER_TIME"
const val TIMER_UNIQUE_NAME = "TIMER_UNIQUE_NAME"

class TimerFragment : Fragment() {

    private var overallTimeInSeconds = 0
    private var timerState = TimerState.STOP

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_timer, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkBattery(requireContext())

        createTimerObserver()

        setupNumberPickers()
        setupPlayButtonSwitcher()
        setupRefreshButton()
    }

    private fun getWorkManager() = WorkManager.getInstance(requireContext())

    private fun createTimerObserver() {
        getWorkManager()
            .getWorkInfosForUniqueWorkLiveData(TIMER_UNIQUE_NAME)
            .observe(viewLifecycleOwner, Observer { workInfo ->
                workInfo.getOrNull(0)?.let {
                    overallTimeInSeconds = it.progress.getInt(TIMER_TIME, 0)

                    if (overallTimeInSeconds != 0)
                        timer_text.text = overallTimeInSeconds.toTimeFormatString()
                    Log.d(
                        "WorkerProgress",
                        "${it.state} $overallTimeInSeconds, timerState: $timerState"
                    )
                    workStateHandler(it.state)
                }
            })
    }

    private fun workStateHandler(state: WorkInfo.State) {
        when (state) {
            WorkInfo.State.SUCCEEDED -> if (timerState == TimerState.START || timerState == TimerState.RESUME) {
                timer_text.text = getString(R.string.default_time)
                timer_finish_text.visibility = View.VISIBLE
                play_button_switcher.visibility = View.GONE
            }
            else -> Unit
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
                changePickersVisibility(false)

                refresh_image_button.visibility = View.VISIBLE

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
        refresh_image_button.setOnClickListener { refreshButton ->
            changePickersVisibility(true)

            refreshButton.visibility = View.GONE
            timer_finish_text.visibility = View.GONE
            play_button_switcher.visibility = View.VISIBLE

            if (play_button_switcher.currentView == pause_image)
                play_button_switcher.showNext()

            changeState(TimerState.STOP)
        }
    }

    private fun changePickersVisibility(visibility: Boolean) {
        if (visibility) {
            hours_picker.visibility = View.VISIBLE
            minutes_picker.visibility = View.VISIBLE
            seconds_picker.visibility = View.VISIBLE
            hours_picker.value = MIN_TIME
            minutes_picker.value = MIN_TIME
            seconds_picker.value = MIN_TIME
        } else {
            hours_picker.visibility = View.GONE
            minutes_picker.visibility = View.GONE
            seconds_picker.visibility = View.GONE
        }
    }

    private fun changeState(timerState: TimerState) {
        this.timerState = timerState

        when (this.timerState) {
            TimerState.START -> {
                overallTimeInSeconds = getTimeInMilliseconds()
                startTimerWorker()
            }
            TimerState.PAUSE -> getWorkManager().cancelUniqueWork(TIMER_UNIQUE_NAME)
            TimerState.RESUME -> startTimerWorker()
            TimerState.STOP -> {
                getWorkManager().cancelUniqueWork(TIMER_UNIQUE_NAME)
                timer_text.text = getString(R.string.default_time)
            }
        }
    }

    private fun startTimerWorker() {
        WorkManager.getInstance(requireContext())
            .enqueueUniqueWork(
                TIMER_UNIQUE_NAME,
                ExistingWorkPolicy.REPLACE,
                OneTimeWorkRequestBuilder<TimerWorker>()
                    .setInputData(Data.Builder().putInt(TIMER_TIME, overallTimeInSeconds).build())
                    .build()
            )
    }

    private fun getTimeInMilliseconds(): Int {
        val secondsInHour = 3600
        val secondsInMinute = 60
        return hours_picker.value * secondsInHour + minutes_picker.value * secondsInMinute + seconds_picker.value
    }

}