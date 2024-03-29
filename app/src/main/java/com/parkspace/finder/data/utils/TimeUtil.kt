package com.parkspace.finder.data.utils

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
fun isStartTimeBeforeEndTime(startTime: TimePickerState, endTime: TimePickerState): Boolean {
    return if (startTime.hour < endTime.hour) {
        true
    } else if (startTime.hour == endTime.hour) {
        startTime.minute < endTime.minute
    } else {
        false
    }
}

@OptIn(ExperimentalMaterial3Api::class)
fun formatTime(timePickerState: TimePickerState): String {
    val formatter = SimpleDateFormat("hh:mm a", Locale.getDefault())
    val cal = Calendar.getInstance()
    cal.set(Calendar.HOUR_OF_DAY, timePickerState.hour)
    cal.set(Calendar.MINUTE, timePickerState.minute)
    cal.isLenient = false
    return formatter.format(cal.time)
}

@OptIn(ExperimentalMaterial3Api::class)
fun calculateDuration(startTimeStr: String, endTimeStr: String): String {
    val startTime = SimpleDateFormat("hh:mm a", Locale.getDefault()).parse(startTimeStr)
    val endTime = SimpleDateFormat("hh:mm a", Locale.getDefault()).parse(endTimeStr)
    if(startTime != null && endTime != null) {
        val diff = endTime.time - startTime.time
        val hours = diff / (60 * 60 * 1000)
        val minutes = (diff - hours * (60 * 60 * 1000)) / (60 * 1000)
        return "$hours hours $minutes minutes"
    }
    return ""
}