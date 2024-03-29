package com.parkspace.finder.data

import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@HiltViewModel
class BookingViewModel @Inject constructor() : ViewModel() {
    val vehicleOptions = listOf(
        "SUV",
        "Hatchback",
        "Minivan",
        "Sedan",
        "Crossover",
        "Coupe",
    )
    private var _vehicleSelection = MutableStateFlow<String>("")
    var vehicleSelection: StateFlow<String> = _vehicleSelection

    private var _dateSelection = MutableStateFlow<LocalDate?> (null)
    var dateSelection: StateFlow<LocalDate?> = _dateSelection

    private var _startTimeSelection = MutableStateFlow<TimePickerState>(TimePickerState(LocalTime.now().hour, LocalTime.now().minute, true))
    var startTimeSelection: StateFlow<TimePickerState> = _startTimeSelection

    private var _endTimeSelection = MutableStateFlow<TimePickerState>(TimePickerState(LocalTime.now().plusHours(2).hour, LocalTime.now().plusHours(2).minute, true))
    var endTimeSelection: StateFlow<TimePickerState> = _endTimeSelection

    init {
        _vehicleSelection.value = vehicleOptions[0]
        _dateSelection.value = LocalDate.now()
        _startTimeSelection.value = TimePickerState(LocalTime.now().hour, LocalTime.now().minute, true)
        _endTimeSelection.value = TimePickerState(LocalTime.now().plusHours(2).hour, LocalTime.now().plusHours(2).minute, true)
    }

    fun onVehicleSelectionChanged(option: String) {
        Log.d("BookingViewModel", "Vehicle selection changed to $option")
        _vehicleSelection.value = option
    }

    fun onDateSelectionChanged(date: LocalDate) {
        Log.d("BookingViewModel", "Date selection changed to $date")
        _dateSelection.value = date
    }

    fun onStartTimeSelectionChanged(time: TimePickerState) {
        Log.d("BookingViewModel", "Time selection changed to $time")
        _startTimeSelection.value = time
    }

    fun onEndTimeSelectionChanged(time: TimePickerState) {
        Log.d("BookingViewModel", "Time selection changed to $time")
        _endTimeSelection.value = time
    }
}