package com.parkspace.finder.ui.booking

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Colors
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.parkspace.finder.ui.theme.CustomShapes
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnterDetailsContent(navController : NavController, onViewTicketClick: () -> Unit) {
    var isDateSelected by rememberSaveable { mutableStateOf(false) }
    var isTimeSelected by rememberSaveable { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf<Date?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Enter Details heading
        Text(
            text = "Enter Details",
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(top = 16.dp)
        )


        Column(modifier = Modifier.align(Alignment.Start)) {


            DatePickerDialog(onDateSelected = {})
            Spacer(modifier = Modifier.size(20.dp))


            TimePickerDialog(onTimeSelected = { /* handle selected time */ })
            Spacer(modifier = Modifier.size(20.dp))
            EndTimePickerDialog(onTimeSelected = { /* handle selected time */ })

        }


        Spacer(modifier = Modifier.weight(1f))

        // Cancel and Confirm buttons
        Button(
            onClick = { /* Handle Cancel button click */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)) {
            Text("Cancel")
        }
        Button(
            onClick = { /* Handle Confirm button click */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
        ) {
            Text("Confirm")
        }
    }
}
