package com.parkspace.finder.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parkspace.finder.data.DataStoreRepository
import com.parkspace.finder.navigation.ROUTE_LOGIN
import com.parkspace.finder.navigation.ROUTE_ONBOARDING
import com.parkspace.finder.navigation.ROUTE_PARKING_TIMER
import com.parkspace.finder.navigation.ROUTE_PAYMENT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for managing splash screen logic.
 *
 * @property dataStoreRepository Repository for accessing data store.
 */
class SplashViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel(){
    private val _isLoading: MutableState<Boolean> = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    private val _startDestination: MutableState<String> = mutableStateOf(ROUTE_ONBOARDING)
    val startDestination: State<String> = _startDestination

    init {
        viewModelScope.launch {
            dataStoreRepository.readOnBoardingState().collect { onBoardingCompleted ->
                if(onBoardingCompleted) {
                    _startDestination.value = ROUTE_LOGIN
                } else {
                    _startDestination.value = ROUTE_ONBOARDING
                }
            }
            _isLoading.value = false
        }
    }
}