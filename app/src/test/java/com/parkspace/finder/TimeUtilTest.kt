package com.parkspace.finder

// Import statements for the required classes and functions
import androidx.compose.material3.ExperimentalMaterial3Api
import org.junit.Test
import androidx.compose.material3.TimePickerState
import com.parkspace.finder.data.utils.calculateDuration
import com.parkspace.finder.data.utils.formatTime
import com.parkspace.finder.data.utils.isStartTimeBeforeEndTime
import org.junit.Assert.assertEquals

// Annotation to use experimental material3 API features
/**
 * A test class for various time-related utilities.
 *
 * This class contains unit tests that verify the functionality of time comparison,
 * formatting, and duration calculation within the Parkspace Finder application.
 * It utilizes the ExperimentalMaterial3Api for time picker components.
 */
@OptIn(ExperimentalMaterial3Api::class)
class TimeUtilTest {

    /**
     * Tests whether the method correctly identifies when a start time is before an end time.
     *
     * This test initializes two TimePickerState instances representing the start and end times
     * and checks if the method correctly evaluates that the start time precedes the end time.
     */
    @Test
    fun testIsStartTimeBeforeEndTime() {
        val startTime = TimePickerState(10, 30, true)
        val endTime = TimePickerState(12, 0, true)
        val result = isStartTimeBeforeEndTime(startTime, endTime)
        assertEquals(true, result)
    }

    /**
     * Tests the formatting of time from a TimePickerState object to a string representation.
     *
     * This test ensures that the `formatTime` function correctly formats a given TimePickerState
     * instance into a string in the format of "hh:mm a".
     */
    @Test
    fun testFormatTime() {
        val timePickerState = TimePickerState(14, 45, true)
        val result = formatTime(timePickerState)
        assertEquals("02:45 pm", result)
    }

    /**
     * Tests the calculation of duration between two time strings.
     *
     * This test verifies that the `calculateDuration` function accurately calculates the time
     * duration between two given time strings and outputs the result in the format of
     * "X hours Y minutes".
     */
    @Test
    fun testCalculateDuration() {
        val startTimeStr = "09:30 AM"
        val endTimeStr = "11:45 AM"
        val result = calculateDuration(startTimeStr, endTimeStr)
        assertEquals("2 hours 15 minutes", result)
    }
}
