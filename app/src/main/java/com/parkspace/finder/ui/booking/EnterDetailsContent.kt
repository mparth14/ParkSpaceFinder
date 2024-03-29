package com.parkspace.finder.ui.booking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavController


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.parkspace.finder.data.BookingViewModel
import java.util.Date

@Composable
fun EnterDetailsContent(navController : NavController, bookingViewModel: BookingViewModel = hiltViewModel()) {

    Surface(modifier = Modifier
        .fillMaxSize()
        .background(color = MaterialTheme.colorScheme.surface)) {
        Column(modifier = Modifier.padding(4.dp))  {
            BookingHeader()
            VehicleSelection(bookingViewModel)
            DaySelector(bookingViewModel)
            TimeSelection(bookingViewModel)
        }
        PriceBottomBar()
    }
}
