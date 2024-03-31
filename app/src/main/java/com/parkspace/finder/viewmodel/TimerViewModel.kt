package com.parkspace.finder.ui.timerParkingBooking

import android.os.CountDownTimer
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.util.concurrent.TimeUnit

class TimerViewModel : ViewModel() {

    private var timer: CountDownTimer? = null

    var timeLeftMillis by mutableStateOf(0L)
        private set

    var isTimerRunning by mutableStateOf(false)
        private set

    val totalTimeMillis: Long = TimeUnit.MINUTES.toMillis(30) // Example: 30 minutes

    fun startTimer(durationMillis: Long) {
        timer?.cancel()

        timer = object : CountDownTimer(durationMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftMillis = millisUntilFinished
            }

            override fun onFinish() {
                timeLeftMillis = 0
                isTimerRunning = false
            }
        }.start()

        isTimerRunning = true
    }

    fun stopTimer() {
        timer?.cancel()
        isTimerRunning = false
    }
}
