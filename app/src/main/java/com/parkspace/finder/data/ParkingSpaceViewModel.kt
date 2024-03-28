package com.parkspace.finder.data

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Looper
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.parkspace.finder.data.utils.distanceBetween
import com.parkspace.finder.navigation.ROUTE_REQUEST_LOCATION_PERMISSION
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ParkingSpaceViewModel @Inject constructor(
    @ApplicationContext context: Context,
    val repository: ParkingSpaceRepository
) : ViewModel() {
    suspend fun getParkingSpaceByName(name: String): ParkingSpace? {
        // Assuming repository provides a function to fetch by name
        return repository.getParkingSpaceByName(name)
    }
    private val _parkingSpaces = MutableStateFlow<Resource<List<ParkingSpace>>?>(null)
    val parkingSpaces: StateFlow<Resource<List<ParkingSpace>>?> = _parkingSpaces


    val permissions = arrayOf(
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )

    private val _needsLocationPermission = MutableStateFlow<Boolean>(true)
    val needsLocationPermission: StateFlow<Boolean> = _needsLocationPermission

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private var locationPermissionRequired: Boolean = false

    var _currentLocation = MutableStateFlow<LatLng>(LatLng(0.0, 0.0))
    val currentLocation: StateFlow<LatLng> = _currentLocation

    private lateinit var geocoder: Geocoder
    private var _addresses = MutableStateFlow<List<Address>>(emptyList())
    var addresses: StateFlow<List<Address>> = _addresses

    @Suppress("MissingPermission")
    private fun startLocationUpdates() {
        Log.d("ParkingSpaceViewModel", "startLocationUpdates")
        locationCallback?.let {
            val locationRequest = LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY, 100
            ).setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(3000)
                .build()

            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                it,
                Looper.getMainLooper()
            )
        }
    }

    init {
        geocoder = Geocoder(context, Locale.getDefault())
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult:  LocationResult) {
                super.onLocationResult(locationResult)
                for (location in locationResult.locations) {
                    _currentLocation.value = LatLng(location.latitude, location.longitude)
                    _addresses.value =
                        geocoder.getFromLocation(location.latitude, location.longitude, 1)!!
                    sortParkingSpaces()
                }
            }
        }
        _needsLocationPermission.value = !permissions.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
        fetchParkingSpaces()
        if (!_needsLocationPermission.value) {
            startLocationUpdates()
        }
    }

    fun sortParkingSpaces() {
        _parkingSpaces.value?.let { spaces ->
            if(spaces is Resource.Success) {
                spaces.result?.forEach {
                    val sortedSpaces = spaces.result.sortedBy {
                        it.distanceFromCurrentLocation  = distanceBetween(_currentLocation.value, LatLng(it.location.latitude, it.location.longitude))
                        it.distanceFromCurrentLocation
                    }
                    _parkingSpaces.value = Resource.Success(sortedSpaces)
                }
            }
        }
    }

    fun fetchParkingSpaces() {
        viewModelScope.launch {
            _parkingSpaces.value = Resource.Loading
            val result = repository.getParkingSpaces()
            _parkingSpaces.value = result
            sortParkingSpaces()
        }
    }
}