package com.parkspace.finder.data.utils

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState

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