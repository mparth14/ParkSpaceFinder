package com.parkspace.finder.ui.booking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.parkspace.finder.data.BookingViewModel

@Composable
fun EnterDetailsContent(
    navController: NavHostController, parkingId: String,
) {
    val bookingViewModel = hiltViewModel<BookingViewModel, BookingViewModel.Factory> {
        it.create(parkingId)
    }
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
        PriceBottomBar(navController, bookingViewModel)
    }
}
