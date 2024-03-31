/**
 * Utility functions for handling time-related operations and calculations.
 */
package com.parkspace.finder.data.utils

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * Checks if the start time is before the end time.
 * @param startTime The state representing the start time.
 * @param endTime The state representing the end time.
 * @return True if the start time is before the end time, false otherwise.
 */
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

/**
 * Formats the given time picker state into a string.
 * @param timePickerState The state representing the time.
 * @return The formatted time string.
 */
@OptIn(ExperimentalMaterial3Api::class)
fun formatTime(timePickerState: TimePickerState): String {
    val formatter = SimpleDateFormat("hh:mm a", Locale.getDefault())
    val cal = Calendar.getInstance()
    cal.set(Calendar.HOUR_OF_DAY, timePickerState.hour)
    cal.set(Calendar.MINUTE, timePickerState.minute)
    cal.isLenient = false
    return formatter.format(cal.time)
}

/**
 * Calculates the duration between two time strings.
 * @param startTimeStr The start time string.
 * @param endTimeStr The end time string.
 * @return The duration between the two times.
 */
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

fun calculateDurationInHours(startTimeStr: String, endTimeStr: String): Double {
    val startTime = SimpleDateFormat("hh:mm a", Locale.getDefault()).parse(startTimeStr)
    val endTime = SimpleDateFormat("hh:mm a", Locale.getDefault()).parse(endTimeStr)
    if(startTime != null && endTime != null) {
        val diff = endTime.time - startTime.time
        val hours: Double = diff / (60 * 60 * 1000.0)
        val minutes = (diff - hours * (60 * 60 * 1000)) / (60 * 1000)
        return hours + (minutes / 60.0)
    }
    return 0.0
}


/**
 * Checks if the given date and time is in the past relative to the current date and time.
 * @param date The date string in the format "yyyy-MM-dd".
 * @param endTime The time string in the format "hh:mm a".
 * @return True if the given date and time is in the past, false otherwise.
 */
@OptIn(ExperimentalMaterial3Api::class)
fun checkIfDateTimeIsInPast(date: String, endTime: String): Boolean {
    val formatter = SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.getDefault())

    val cal = Calendar.getInstance()
    val calEnd = Calendar.getInstance()
    if(date.isNotEmpty() && endTime.isNotEmpty()) {
        val dateAndTime = "$date $endTime"
        val dateTime = formatter.parse(dateAndTime)
        if(dateTime != null) {
            cal.time = dateTime
            calEnd.time = Calendar.getInstance().time
            return cal.before(calEnd)
        }
    }
    return false

}
