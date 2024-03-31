package com.parkspace.finder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parkspace.finder.data.ParkingSpace
import com.parkspace.finder.data.ParkingSpaceRepository
import com.parkspace.finder.data.Resource
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = ParkingDetailViewModel.Factory::class)
class ParkingDetailViewModel @AssistedInject constructor(
    @Assisted private val parkingId: String,
    private val parkingSpaceRepository: ParkingSpaceRepository
) : ViewModel(){
    @AssistedFactory
    interface Factory {
        fun create(parkingId: String): ParkingDetailViewModel
    }

    // ViewModel logic here
    private val _parkingSpace = MutableStateFlow<Resource<ParkingSpace?>?>(null)
    val parkingSpace: StateFlow<Resource<ParkingSpace?>?> = _parkingSpace
    init {
        viewModelScope.launch {
            _parkingSpace.value = Resource.Loading
            _parkingSpace.value = parkingSpaceRepository.getParkingSpaceById(parkingId)
        }
    }
}