package com.parkspace.finder.viewmodel

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parkspace.finder.data.AuthRepository
import com.parkspace.finder.data.BookingDetailRepository
import com.parkspace.finder.data.BookingDetails
import com.parkspace.finder.data.ParkingSpace
import com.parkspace.finder.data.ParkingSpaceRepository
import com.parkspace.finder.data.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsible for managing data related to all bookings and parking spaces.
 *
 * @property bookingDetailRepository Repository for accessing booking details data.
 * @property authRepository Repository for authentication related operations.
 * @property parkingSpaceRepository Repository for accessing parking space data.
 */
@HiltViewModel
class AllBookingsDetailViewModel @Inject constructor(
    private val bookingDetailRepository: BookingDetailRepository,
    private val authRepository: AuthRepository,
    private val parkingSpaceRepository: ParkingSpaceRepository
) : ViewModel(){
    private val _bookings = MutableStateFlow<Resource<List<BookingDetails>>?>(null)
    val bookings: StateFlow<Resource<List<BookingDetails>>?> = _bookings

    private val _parkingSpaces = MutableStateFlow<Resource<Map<String, ParkingSpace>>?>(null)
    val parkingSpaces: StateFlow<Resource<Map<String, ParkingSpace>>?> = _parkingSpaces

    init {
        viewModelScope.launch {
            _bookings.value = Resource.Loading
            _parkingSpaces.value = Resource.Loading
            _bookings.value = bookingDetailRepository.getBookingDetailsByEmail(authRepository?.currentUser?.email ?: "")
            val parkingSpaceResult = parkingSpaceRepository.getParkingSpaces()
            if(parkingSpaceResult is Resource.Success) {
                val res = parkingSpaceResult.result
                val parkingSpaceMap = res.associateBy { it.id }
                _parkingSpaces.value = Resource.Success(parkingSpaceMap)
            }
        }
    }
}