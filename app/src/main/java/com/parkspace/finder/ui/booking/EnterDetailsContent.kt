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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnterDetailsContent(navController : NavController, onViewTicketClick: () -> Unit) {
    var isDateSelected by rememberSaveable { mutableStateOf(false) }
    var isTimeSelected by rememberSaveable { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf<Date?>(null) }

    Surface(modifier = Modifier
        .fillMaxSize()
        .background(color = MaterialTheme.colorScheme.surface)) {
        Column(modifier = Modifier.padding(4.dp))  {
            BookingHeader()
            VehicleSelection()
            DaySelector()
            TimeSelection()

        }
        PriceBottomBar()
    }

//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.spacedBy(16.dp)
//    ) {
//        // Enter Details heading
//        Text(
//            text = "Enter Details",
//            style = MaterialTheme.typography.h4,
//            modifier = Modifier.padding(top = 16.dp)
//        )
//
//
//        Column(modifier = Modifier.align(Alignment.Start)) {
//
//
//            DatePickerDialog(onDateSelected = {})
//            Spacer(modifier = Modifier.size(20.dp))
//
//
//            TimePickerDialog(onTimeSelected = { /* handle selected time */ })
//            Spacer(modifier = Modifier.size(20.dp))
//            EndTimePickerDialog(onTimeSelected = { /* handle selected time */ })
//
//        }
//
//
//        Spacer(modifier = Modifier.weight(1f))
//
//        // Cancel and Confirm buttons
//        Button(
//            onClick = { /* Handle Cancel button click */ },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 20.dp)) {
//            Text("Cancel")
//        }
//        Button(
//            onClick = { /* Handle Confirm button click */ },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 20.dp),
//        ) {
//            Text("Confirm")
//        }
//    }
}
