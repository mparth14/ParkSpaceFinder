package com.parkspace.finder.ui.booking

import android.app.TimePickerDialog
import android.widget.TimePicker
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.android.material.timepicker.MaterialTimePicker
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeSelection() {
    val showTimePicker = remember { mutableStateOf(false)}
    val state = rememberTimePickerState()
    val snackState = remember { SnackbarHostState() }
    val snackScope = rememberCoroutineScope()
    val formatter = remember { SimpleDateFormat("hh:mm a", Locale.getDefault()) }
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
                    .clickable { showTimePicker.value = true }
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
                        text = "8:00 AM",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
            Surface(
                modifier = Modifier
                    .defaultMinSize(160.dp)
                    .clickable { },
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
                        text = "12:00 PM",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
            if(showTimePicker.value) {
                TimePickerDialog(
                    onCancel = { showTimePicker.value = false },
                    onConfirm = {
                        val cal = Calendar.getInstance()
                        cal.set(Calendar.HOUR_OF_DAY, state.hour)
                        cal.set(Calendar.MINUTE, state.minute)
                        cal.isLenient = false
                        snackScope.launch {
                            snackState.showSnackbar("Entered time: ${formatter.format(cal.time)}")
                        }
                        showTimePicker.value = false
                    },
                ) {
                    TimePicker(state = state)
                }
            }
        }
    }
}