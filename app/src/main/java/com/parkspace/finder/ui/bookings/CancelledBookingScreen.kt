package com.parkspace.finder.ui.bookings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.parkspace.finder.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CancelledBookingScreen(bookingId: String?) {
    val bookingItems = listOf(
        BookingItem("1", "Blue Skies Parking", "Monday, October 24", "8:00 AM - 12:00 PM", "$60", "Confirmed"),
        BookingItem("2", "North Cerulean District", "Saturday, October 22", "8:00 AM - 7:00 PM", "$24", "Completed"),
        BookingItem("3", "Splitter Garage", "Friday, October 21", "8:00 AM - 4:00 PM", "$45", "Cancelled"),
        BookingItem("4", "Park It Down", "Wednesday, October 19", "8:00 AM - 12:00 PM", "$34", "Completed"),
        BookingItem("5", "Jester Park", "Tuesday, October 18", "8:00 AM - 11:00 AM", "$64", "Confirmed")
    )
    val booking = bookingItems.first { it.id == bookingId }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Parking Details") },
                navigationIcon = {
                    IconButton(onClick = { /* Handle back press */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
        ) {
            BookingInfoSection(booking)
            Spacer(modifier = Modifier.height(1.dp))
            BookingDateAndTime(booking)
            Spacer(modifier = Modifier.height(1.dp))
            PriceSection(booking)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CancelledDetailedBookingScreenPreview() {
    MaterialTheme {
        CancelledBookingScreen("1")
    }
}
