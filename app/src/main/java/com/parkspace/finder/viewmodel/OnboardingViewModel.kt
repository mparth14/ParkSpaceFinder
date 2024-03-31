package com.parkspace.finder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parkspace.finder.data.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsible for managing onboarding state.
 *
 * @property dataStoreRepository Repository for accessing data store.
 */
@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel(){
    fun saveOnboardingState(completed: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveOnBoardingState(completed)
        }
    }
}