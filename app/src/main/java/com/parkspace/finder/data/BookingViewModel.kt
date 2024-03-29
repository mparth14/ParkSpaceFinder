package com.parkspace.finder.data

import android.app.Activity
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.parkspace.finder.data.utils.formatTime
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@HiltViewModel(assistedFactory = BookingViewModel.Factory::class)
class BookingViewModel @AssistedInject constructor(
    @Assisted private val parkingId: String,
    private val parkingSpaceRepository: ParkingSpaceRepository,
    private val bookingDetailRepository: BookingDetailRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _parkingSpace = MutableStateFlow<Resource<ParkingSpace?>?>(null)
    val parkingSpace: StateFlow<Resource<ParkingSpace?>?> = _parkingSpace
    @AssistedFactory
    interface Factory {
        fun create(parkingId: String): BookingViewModel
    }

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

    private var _bookingStatus = MutableStateFlow<Resource<String>?>(null)
    val bookingStatus: StateFlow<Resource<String>?> = _bookingStatus

    init {
        viewModelScope.launch {
            _parkingSpace.value = Resource.Loading
            _parkingSpace.value  = parkingSpaceRepository.getParkingSpaceById(parkingId)
        }
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

    fun saveBooking() {
        val space = (parkingSpace.value as Resource.Success<ParkingSpace?>).result
        val bookingDetails = BookingDetails(
            startTime = formatTime(startTimeSelection.value),
            endTime = formatTime(endTimeSelection.value),
            spotNumber = (1..10000).random().toString(),
            price = space?.hourlyPrice ?: 0.0,
            lotId = space?.id ?: "",
            bookingDate = dateSelection.value.toString(),
            vehicleType = vehicleSelection.value,
            userEmail = authRepository.currentUser?.email ?: ""
        )
        viewModelScope.launch {
            _bookingStatus.value = Resource.Loading
            _bookingStatus.value =  bookingDetailRepository.makeBooking(bookingDetails)
        }
    }
}
