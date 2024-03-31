package com.parkspace.finder.ui.booking

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.parkspace.finder.data.BookingViewModel
import java.time.LocalDate
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
object PresentOrFutureSelectableDates : SelectableDates {
    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        return utcTimeMillis >= System.currentTimeMillis()
    }

    override fun isSelectableYear(year: Int): Boolean {
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        return year >= calendar.get(Calendar.YEAR)
    }
}

/**
 * Composable function for selecting the day of booking.
 *
 * @param bookingViewModel: ViewModel for booking data.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DaySelector(bookingViewModel: BookingViewModel) {
    val openDialog = remember { mutableStateOf(false) }
    var dateSelection = bookingViewModel.dateSelection.collectAsState()
    var isTodaySelected = dateSelection.value.toString() == LocalDate.now().toString()
    var isTomorrowSelected =
        dateSelection.value.toString() == LocalDate.now().plusDays(1).toString()
    Column(modifier = Modifier.padding(8.dp)) {
        Text(
            text = "Day",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
        )
        Column(
//            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .clickable { bookingViewModel.onDateSelectionChanged(LocalDate.now()) },
                color = if (isTodaySelected) MaterialTheme.colorScheme.primary else Color.White,
                border = BorderStroke(
                    1.dp,
                    if (false) MaterialTheme.colorScheme.primary else Color.LightGray
                ),
                shape = RoundedCornerShape(5.dp)
            ) {
                Text(
                    text = "Today",
                    modifier = Modifier.padding(horizontal = 22.dp, vertical = 14.dp),
                    color = if (isTodaySelected) Color.White else Color.Black,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )
            }
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .clickable {
                        bookingViewModel.onDateSelectionChanged(
                            LocalDate
                                .now()
                                .plusDays(1)
                        )
                    },
                color = if (isTomorrowSelected) MaterialTheme.colorScheme.primary else Color.White,
                border = BorderStroke(
                    1.dp,
                    if (isTomorrowSelected) MaterialTheme.colorScheme.primary else Color.LightGray
                ),
                shape = RoundedCornerShape(5.dp)
            ) {
                Text(
                    text = "Tomorrow",
                    modifier = Modifier.padding(horizontal = 22.dp, vertical = 14.dp),
                    color = if (isTomorrowSelected) Color.White else Color.Black,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )
            }
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .clickable { openDialog.value = true },
                color = if (!isTodaySelected && !isTomorrowSelected) MaterialTheme.colorScheme.primary else Color.White,
                border = BorderStroke(
                    1.dp,
                    if (!isTodaySelected && !isTomorrowSelected) MaterialTheme.colorScheme.primary else Color.LightGray
                ),
                shape = RoundedCornerShape(5.dp)
            ) {
                if (!isTodaySelected && !isTomorrowSelected) {
                    Text(
                        text = dateSelection.value.toString(),
                        modifier = Modifier.padding(horizontal = 22.dp, vertical = 14.dp),
                        color = Color.White,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                } else {
                    Text(
                        text = "Later",
                        modifier = Modifier.padding(horizontal = 22.dp, vertical = 14.dp),
                        color = if (!isTodaySelected && !isTomorrowSelected) Color.White else Color.Black,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
            if (openDialog.value) {
                val calendar = Calendar.getInstance()
                calendar.time = Date()
                val datePickerState = rememberDatePickerState(
                    selectableDates = PresentOrFutureSelectableDates
                )
                val confirmEnabled = remember {
                    derivedStateOf { datePickerState.selectedDateMillis != null }
                }
                DatePickerDialog(
                    onDismissRequest = {
                        openDialog.value = false
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                openDialog.value = false
                                datePickerState.selectedDateMillis?.let {
                                    bookingViewModel.onDateSelectionChanged(LocalDate.ofEpochDay(it / 86400000))
                                }
                            },
                            enabled = confirmEnabled.value
                        ) {
                            Text("OK")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                openDialog.value = false
                            }
                        ) {
                            Text("Cancel")
                        }
                    }
                ) {
                    DatePicker(state = datePickerState)
                }
            }
        }
    }

}
