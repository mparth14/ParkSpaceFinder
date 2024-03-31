package com.parkspace.finder.ui.booking

import android.app.TimePickerDialog
import android.util.Log
import android.widget.TimePicker
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.material.timepicker.MaterialTimePicker
import com.parkspace.finder.data.BookingViewModel
import com.parkspace.finder.data.utils.isStartTimeBeforeEndTime
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeSelection(bookingViewModel: BookingViewModel) {
    val showTimePicker = remember { mutableStateOf(false)}
    val isStartTimeSelected = remember { mutableStateOf(true)}
    val startTimeSelection = bookingViewModel.startTimeSelection.collectAsState()
    val endTimeSelection = bookingViewModel.endTimeSelection.collectAsState()

    val formatter = remember { SimpleDateFormat("hh:mm a", Locale.getDefault()) }
    val formattedStartTime = remember(startTimeSelection) {
        derivedStateOf {
            val cal = Calendar.getInstance()
            cal.set(Calendar.HOUR_OF_DAY, startTimeSelection.value.hour)
            cal.set(Calendar.MINUTE, startTimeSelection.value.minute)
            cal.isLenient = false
            formatter.format(cal.time)
        }
    }
    val formattedEndTime = remember(endTimeSelection) {
        derivedStateOf {
            val cal = Calendar.getInstance()
            cal.set(Calendar.HOUR_OF_DAY, endTimeSelection.value.hour)
            cal.set(Calendar.MINUTE, endTimeSelection.value.minute)
            cal.isLenient = false
            formatter.format(cal.time)
        }
    }
    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        Text(
            text = "Time",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
        )
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Surface(
                modifier = Modifier
                    .defaultMinSize(160.dp)
                    .weight(1f)
                    .clickable {
                        isStartTimeSelected.value = true
                        showTimePicker.value = true
                    }
                ,
                border = BorderStroke(1.dp, Color.LightGray),
                shape = RoundedCornerShape(5.dp)
            ) {
                Column (
                    modifier = Modifier.padding(12.dp)
                ) {
                    Text(
                        text = "Start",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
                    )
                    Text(
                        text = formattedStartTime.value,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Surface(
                modifier = Modifier
                    .defaultMinSize(160.dp)
                    .weight(1f)
                    .clickable {
                        isStartTimeSelected.value = false
                        showTimePicker.value = true
                    },
                border = BorderStroke(1.dp, Color.LightGray),
                shape = RoundedCornerShape(5.dp)
            ) {
                Column (
                    modifier = Modifier.padding(12.dp)
                ) {
                    Text(
                        text = "End",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
                    )
                    Text(
                        text = formattedEndTime.value,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
            if(showTimePicker.value) {
                TimePickerDialog(
                    onCancel = { showTimePicker.value = false },
                    onConfirm = {
                        showTimePicker.value = false
                    },
                ) {
                    TimePicker(state = if(isStartTimeSelected.value) bookingViewModel.startTimeSelection.value else bookingViewModel.endTimeSelection.value)
                }
            }
        }
        if(!isStartTimeBeforeEndTime(startTimeSelection.value, endTimeSelection.value)) {
            Text(
                text = "End time should be after start time",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp),
                fontSize = 12.sp
            )
        }
    }
}