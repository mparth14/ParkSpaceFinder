package com.parkspace.finder.ui.search
import android.app.TimePickerDialog
import android.os.Build
import android.util.Log

import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.parkspace.finder.data.AuthViewModel
import com.parkspace.finder.ui.theme.*
import androidx.compose.ui.text.input.ImeAction
import androidx.hilt.navigation.compose.hiltViewModel
import com.parkspace.finder.data.ParkingSpaceViewModel
import java.util.*




@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParkingBookingScreen(navController: NavHostController,parkingSpaceViewModel: ParkingSpaceViewModel) {
    //viewModel: AuthViewModel?, navController: NavHostController
    var hint: String = "Search for everything..."
    var text by remember { mutableStateOf("") }
    var isHintDisplayed by remember { mutableStateOf(hint != "") }
    var place by remember { mutableStateOf("Halifax?") }
    var selectedDate by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf("") }
    var selectedTimeStart by parkingSpaceViewModel.selectedTimeStart
    var selectedDateStart by parkingSpaceViewModel.selectedDateStart
    val dateFormat = parkingSpaceViewModel.dateFormat
    val timeFormat = parkingSpaceViewModel.timeFormat
    val searchQuery by parkingSpaceViewModel.searchQuery.collectAsState()

    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                text = "The Best Parking Place in",
                modifier = Modifier
                    .padding(16.dp),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyLarge

            )
            Text(
                text = place,
                modifier = Modifier
                    .padding(16.dp, 0.dp, 0.dp, 0.dp),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.primary),
                color = md_theme_light_primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    ,
            ) {
                BasicTextField(
                    value = searchQuery,
                    onValueChange = {
                        parkingSpaceViewModel.setSearchQuery(it)
                        isHintDisplayed = it.isEmpty()
                    },
                    maxLines = 1,
                    singleLine = true,
                    textStyle = TextStyle(fontSize = 24.sp, textAlign = TextAlign.Start),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .align(Alignment.CenterStart)
                        .shadow(5.dp, RoundedCornerShape(10.dp))
                        .background(Color.White)
                        .onFocusChanged {
                            isHintDisplayed = !it.isFocused && text.isEmpty()
                        }
                        .padding(top = 10.dp)
                        .padding(start = 10.dp)
                )
                if (isHintDisplayed) {
                    Text(
                        text = hint,
                        color = Color.LightGray,
                        modifier = Modifier
                            .padding(horizontal = 40.dp, vertical = 12.dp)
                            .align(Alignment.CenterStart)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(color = md_theme_light_primary.copy(alpha = 0.2f)), // Set the background color with opacity
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Hurry! Book Now ",
                        color = md_theme_light_onSecondarySurfaceTint,
                        fontWeight = FontWeight.Bold // Making the text bold
                    )
                    Text(
                        text = "lots of discounts waiting",
                        color = md_theme_light_onSecondarySurfaceTint,
                        fontWeight = FontWeight.Normal // Keeping the text normal
                    )
                }
            }

            TransportationIcons()

            Spacer(modifier = Modifier.height(15.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    Text(
                        text = "Booking Details",
                        modifier = Modifier.padding(bottom = 16.dp),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    OutlinedTextField(
                        value = dateFormat.format(selectedDateStart.time),
                        onValueChange = { selectedDate = it },
                        label = { Text("Enter Booking Date") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged { focusState ->
                                if (focusState.isFocused) {
                                    // Open calendar dialog
                                    val datePickerDialog = android.app.DatePickerDialog(
                                        context,
                                        { _, year, month, dayOfMonth ->
                                            // Update selectedDate with the chosen date
                                            selectedDateStart = Calendar
                                                .getInstance()
                                                .apply {
                                                    set(Calendar.YEAR, year)
                                                    set(Calendar.MONTH, month)
                                                    set(Calendar.DAY_OF_MONTH, dayOfMonth)
                                                }
                                        },
                                        Calendar
                                            .getInstance()
                                            .get(Calendar.YEAR),
                                        Calendar
                                            .getInstance()
                                            .get(Calendar.MONTH),
                                        Calendar
                                            .getInstance()
                                            .get(Calendar.DAY_OF_MONTH)
                                    )
                                    datePickerDialog.show()
                                }
                            },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.None),
                        textStyle = TextStyle(fontSize = 16.sp), // Adjust text size
                        placeholder = {
                            Text(
                                text = "Enter Booking Date",
                                color = Color.Gray,
                                fontSize = 16.sp // Adjust placeholder text size
                            )
                        },
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    // Open calendar dialog when trailing icon is clicked
                                    val datePickerDialog = android.app.DatePickerDialog(
                                        context,
                                        { _, year, month, dayOfMonth ->
                                            // Update selectedDate with the chosen date
                                            selectedDateStart = Calendar.getInstance().apply {
                                                set(Calendar.YEAR, year)
                                                set(Calendar.MONTH, month)
                                                set(Calendar.DAY_OF_MONTH, dayOfMonth)
                                            }
                                        },
                                        Calendar.getInstance().get(Calendar.YEAR),
                                        Calendar.getInstance().get(Calendar.MONTH),
                                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                                    )
                                    datePickerDialog.show()
                                }
                            ) {
                                Icon(Icons.Default.DateRange, contentDescription = "Select Date")
                            }
                        }
                    )


                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = selectedTimeStart,
                        onValueChange = { selectedTime = it },
                        label = { Text("Enter Time") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged { focusState ->
                                if (focusState.isFocused) {
                                    // Open time picker dialog
                                    val timePickerDialog = TimePickerDialog(
                                        context,
                                        { _, hourOfDay, minute ->
                                            // Update selectedTime with the chosen time
                                            val selectedCalendar = Calendar
                                                .getInstance()
                                                .apply {
                                                    set(Calendar.HOUR_OF_DAY, hourOfDay)
                                                    set(Calendar.MINUTE, minute)
                                                }
                                            selectedTimeStart =
                                                timeFormat.format(selectedCalendar.time)
                                        },
                                        Calendar
                                            .getInstance()
                                            .get(Calendar.HOUR_OF_DAY),
                                        Calendar
                                            .getInstance()
                                            .get(Calendar.MINUTE),
                                        true
                                    )
                                    timePickerDialog.show()
                                }
                            },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.None),
                        textStyle = TextStyle(fontSize = 16.sp), // Adjust text size
                        placeholder = {
                            Text(
                                text = "Enter Booking Time",
                                color = Color.Gray,
                                fontSize = 16.sp // Adjust placeholder text size
                            )
                        },
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    val timePickerDialog = TimePickerDialog(
                                        context,
                                        { _, hourOfDay, minute ->
                                            // Update selectedTime with the chosen time
                                            val selectedCalendar = Calendar.getInstance().apply {
                                                set(Calendar.HOUR_OF_DAY, hourOfDay)
                                                set(Calendar.MINUTE, minute)
                                            }
                                            selectedTimeStart =
                                                timeFormat.format(selectedCalendar.time)
                                        },
                                        Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                                        Calendar.getInstance().get(Calendar.MINUTE),
                                        true
                                    )
                                    timePickerDialog.show()
                                }

                            ) {
                                Icon(Icons.Default.Timer, contentDescription = "Select Time")
                            }
                        }
                    )
                }

            }



            DurationSelector()

            Button(
                onClick = {
                    parkingSpaceViewModel.setSearchQuery(searchQuery)
                    navController.navigate("browse")
                },
                modifier = Modifier
                    .width(350.dp)
                    .padding(
                        5.dp
                    )
                    .background(
                        color = md_theme_light_primary,
                        shape = MaterialTheme.shapes.small
                    ),
                colors = ButtonDefaults.buttonColors(
                    md_theme_light_primary
                )


            ) {
                Text(
                    text = "Proceed with Search",
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

        }
    }

}

@Composable
@Preview
fun TransportationIcons() {
    val selectedTransportation = remember { mutableStateOf("Car") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        TransportationIcon(
            iconVector = Icons.Default.DirectionsCar,
            label = "Car",
            isSelected = selectedTransportation.value == "Car",
            onSelected = { selectedTransportation.value = "Car" }
        )
        TransportationIcon(
            iconVector = Icons.Default.DirectionsBike,
            label = "Bike",
            isSelected = selectedTransportation.value == "Bike",
            onSelected = { selectedTransportation.value = "Bike" }
        )
        TransportationIcon(
            iconVector = Icons.Default.DirectionsBus,
            label = "Bus",
            isSelected = selectedTransportation.value == "Bus",
            onSelected = { selectedTransportation.value = "Bus" }
        )
        TransportationIcon(
            iconVector = Icons.Default.FireTruck,
            label = "Truck",
            isSelected = selectedTransportation.value == "Truck",
            onSelected = { selectedTransportation.value = "Truck" }
        )
    }
}


@Composable
fun TransportationIcon(
    iconVector: ImageVector,
    label: String,
    isSelected: Boolean,
    onSelected: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isSelected) md_theme_light_primary else md_theme_light_surfaceVariant
    val iconColor = if (isSelected) Color.White else Color.Black

    Column(
        modifier = modifier.clickable { onSelected() }
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(backgroundColor, RoundedCornerShape(10.dp))
                .padding(bottom = 15.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = rememberVectorPainter(image = iconVector),
                contentDescription = label,
                modifier = Modifier.size(35.dp),
                tint = iconColor
            )
            Text(
                modifier = Modifier.align(Alignment.BottomCenter),
                text = label,
                color = iconColor,
                fontSize = 12.sp,
                fontWeight = FontWeight(500)
            )
        }
    }
}


@Composable
@Preview
fun DurationSelector() {
    var selectedDuration by remember { mutableStateOf("") }
    Column {

        Text(
            text = "Select Duration",
            modifier = Modifier.padding(start = 25.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Column {


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DurationChip(
                    text = "Half Day",
                    selected = selectedDuration == "Half Day",
                    modifier = Modifier.weight(1f),
                    onChipClick = { selectedDuration = "Half Day" }
                )
                Spacer(modifier = Modifier.width(16.dp))
                DurationChip(
                    text = "Full Day",
                    selected = selectedDuration == "Full Day",
                    modifier = Modifier.weight(1f),
                    onChipClick = { selectedDuration = "Full Day" }
                )
                Spacer(modifier = Modifier.width(16.dp))
                DurationChip(
                    text = "Multi Day (1-2 days)",
                    selected = selectedDuration == "Multi Day (1-2 days)",
                    modifier = Modifier.weight(1f),
                    onChipClick = { selectedDuration = "Multi Day (1-2 days)" }
                )
            }
        }
    }
}

@Composable
fun DurationChip(
    text: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onChipClick: () -> Unit = {}
) {
    Surface(
        modifier = modifier
            .size(width = 170.dp, height = 95.dp)
            .clickable { onChipClick() }
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        color = if (selected) md_theme_light_background else md_theme_light_surfaceVariant,
        contentColor = if (selected) md_theme_dark_onPrimary else md_theme_light_onPrimary,
        border = if (selected) BorderStroke(2.dp, md_theme_light_primary) else null
    ) {
        Text(
            text = text,
            color = if (selected) md_theme_dark_onPrimary else md_theme_light_secondary,
            fontSize = 14.sp,

            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 8.dp)
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)

        )
    }
}
