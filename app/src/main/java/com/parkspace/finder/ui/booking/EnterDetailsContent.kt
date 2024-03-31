package com.parkspace.finder.ui.booking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface


import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.parkspace.finder.data.BookingViewModel
import com.parkspace.finder.data.utils.isStartTimeBeforeEndTime

/**
 * Composable function for displaying the content of entering booking details.
 *
 * @param navController: NavHostController for navigation.
 * @param parkingId: Identifier for the parking.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnterDetailsContent(
    navController: NavHostController, parkingId: String,
) {
    val bookingViewModel = hiltViewModel<BookingViewModel, BookingViewModel.Factory> {
        it.create(parkingId)
    }
    val startTime = bookingViewModel.startTimeSelection.collectAsState()
    val endTime = bookingViewModel.endTimeSelection.collectAsState()
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(4.dp)) {
            BookingHeader(navController)
            VehicleSelection(bookingViewModel)
            DaySelector(bookingViewModel)
            TimeSelection(bookingViewModel)
        }
        if(isStartTimeBeforeEndTime(startTime.value, endTime.value)){
            PriceBottomBar(navController, bookingViewModel)
        }
    }
}
