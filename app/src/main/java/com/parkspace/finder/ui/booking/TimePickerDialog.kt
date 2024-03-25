package com.parkspace.finder.ui.booking

import android.app.TimePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*

@Composable
fun TimePickerDialog(onTimeSelected: (String) -> Unit) {
    // Fetching local context
    val mContext = LocalContext.current

    // Declaring and initializing a calendar
    val mCalendar = Calendar.getInstance()
    val mHour = mCalendar[Calendar.HOUR_OF_DAY]
    val mMinute = mCalendar[Calendar.MINUTE]

    // Value for storing time as a string
    val mTime = remember { mutableStateOf("") }

    // Creating a TimePicker dialog
    val mTimePickerDialog = TimePickerDialog(
        mContext,
        {_, hourOfDay: Int, minute: Int ->
            if(minute<10)
            {val timeString = "$hourOfDay:0$minute"
            mTime.value = timeString
            onTimeSelected(timeString)}
            else{
                val timeString = "$hourOfDay:$minute"
                mTime.value = timeString
                onTimeSelected(timeString)
            }
        },
        mHour, mMinute, false
    )
    Row( ){

            Button(
                onClick = { mTimePickerDialog.show() },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0000FF))
            ) {
                Text(text = "Start Time", color = Color.White)
            }

            // Add a spacer of 100dp
            Spacer(modifier = Modifier.size(30.dp))

            // Display selected time
            Text(text = "${mTime.value}", fontSize = 30.sp)
            Spacer(modifier = Modifier.size(30.dp))
//    }

        }

}

@Composable
fun EndTimePickerDialog(onTimeSelected: (String) -> Unit) {
    // Fetching local context
    val mContext = LocalContext.current

    // Declaring and initializing a calendar
    val mCalendar = Calendar.getInstance()
    val mHour = mCalendar[Calendar.HOUR_OF_DAY]
    val mMinute = mCalendar[Calendar.MINUTE]

    // Value for storing time as a string
    val mTime = remember { mutableStateOf("") }

    // Creating a TimePicker dialog
    val mTimePickerDialog = TimePickerDialog(
        mContext,
        {_, hourOfDay: Int, minute: Int ->
            if(minute<10)
            {val timeString = "$hourOfDay:0$minute"
                mTime.value = timeString
                onTimeSelected(timeString)}
            else{
                val timeString = "$hourOfDay:$minute"
                mTime.value = timeString
                onTimeSelected(timeString)
            }
        },
        mHour, mMinute, false
    )

    Row( ){

        Button(
            onClick = { mTimePickerDialog.show() },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0000FF))
        ) {
            Text(text = "End  Time", color = Color.White)
        }

        // Add a spacer of 100dp
        Spacer(modifier = Modifier.size(30.dp))

        // Display selected time
        Text(text = " ${mTime.value}", fontSize = 30.sp)

//    }

    }
}