package com.parkspace.finder.data

import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Looper
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
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
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ParkingSpaceViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val repository: ParkingSpaceRepository
) : ViewModel() {
    private val _parkingSpaces = MutableStateFlow<Resource<List<ParkingSpace>>?>(null)
    val parkingSpaces: StateFlow<Resource<List<ParkingSpace>>?> = _parkingSpaces
    private val _filterOptions = MutableStateFlow(FilterOptions())
    val filterOptions: StateFlow<FilterOptions> = _filterOptions
    var selectedTimeStart = mutableStateOf("7:00 AM")
    var selectedTimeEnd = mutableStateOf("9:00 PM")
    var selectedDateEnd = mutableStateOf(Calendar.getInstance())
    var selectedDateStart = mutableStateOf(Calendar.getInstance())
    val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())

    fun updateFilterOptions(filterOptions: FilterOptions) {
        _filterOptions.value = filterOptions
        Log.d("FilterViewModel", "Filter options updated: $filterOptions")
        fetchParkingSpaces()
    }

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
                        it.distanceFromCurrentLocation  = distanceBetween(_currentLocation.value, LatLng(it.location.latitude, it.location.longitude))
                }
            }
        }
    }

    fun fetchParkingSpaces() {
        viewModelScope.launch {
            _parkingSpaces.value = Resource.Loading
            val result = when (filterOptions.value.sortingOption) {
//                "rating" -> repository.getParkingSpacesSortedByRating(
//                    filterOptions.value.sortingOrder == "DESC"
//                )
                "price" -> if (filterOptions.value.sortingOrder == "DESC") {
                    repository.getParkingSpacesSortedByPriceHighToLow()
                } else {
                    repository.getParkingSpacesSortedByPriceLowToHigh()
                }
                else -> repository.getParkingSpaces()
            }
            _parkingSpaces.value = result.filterByOptions(filterOptions.value)
        }
    }

    private fun Resource<List<ParkingSpace>>.filterByOptions(filterOptions: FilterOptions): Resource<List<ParkingSpace>> {
        return when (this) {
            is Resource.Success -> {
                val filteredSpaces = this.result.filter { parkingSpace ->
                    val distance = distanceBetween(
                        _currentLocation.value,
                        LatLng(parkingSpace.location.latitude, parkingSpace.location.longitude)
                    )
                    val distanceFilter = when (filterOptions.distance) {
                        "Within 1km" -> distance <= 1
                        "Any" -> true
                        else -> {
                            val range = filterOptions.distance.substringBefore("-").toDoubleOrNull()?.let { it..it + 1 }
                            range?.let { distance in it } ?: false
                        }
                    }
//                    val durationFilter = when (filterOptions.duration) {
//                        "More than 2 hours" -> true
//                        "1 - 2 hours" -> parkingSpace.duration in 1..2
//                        "<1 hour" -> parkingSpace.duration < 1
//                        else -> false
//                    }
//                    val ratingFilter = filterOptions.rating == 0 || parkingSpace.rating >= filterOptions.rating
                    val priceFilter = parkingSpace.hourlyPrice in filterOptions.priceRange

                    distanceFilter  && priceFilter//&& durationFilter && ratingFilter
                }
                Resource.Success(filteredSpaces)
            }
            is Resource.Failure -> this
            else -> this
        }
    }


        private var _searchQuery = MutableStateFlow("")
        var searchQuery: StateFlow<String> = _searchQuery

        fun setSearchQuery(query: String) {
            _searchQuery.value = query
            searchParkingSpaces(query)
        }

        private fun searchParkingSpaces(query: String) {
            viewModelScope.launch {
                _parkingSpaces.value = Resource.Loading
                val result = repository.searchParkingSpaces(query)
                _parkingSpaces.value = result
                Log.d("yessssssss", result.toString())
            }
        }





}