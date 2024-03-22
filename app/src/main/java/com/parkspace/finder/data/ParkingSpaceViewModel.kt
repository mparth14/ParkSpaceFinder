package com.parkspace.finder.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ParkingSpaceViewModel @Inject  constructor(private val repository: ParkingSpaceRepository) : ViewModel() {
    private val _parkingSpaces = MutableStateFlow<Resource<List<ParkingSpace>>?>(null)
    val parkingSpaces: StateFlow<Resource<List<ParkingSpace>>?> = _parkingSpaces


    init {
        fetchParkingSpaces()
    }
    fun fetchParkingSpaces() {
        viewModelScope.launch {
            _parkingSpaces.value = Resource.Loading
            val result = repository.getParkingSpaces()
            _parkingSpaces.value = result
        }
    }
}