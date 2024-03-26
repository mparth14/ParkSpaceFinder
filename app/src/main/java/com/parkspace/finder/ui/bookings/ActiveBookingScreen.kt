package com.parkspace.finder.ui.bookings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.parkspace.finder.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActiveBookingScreen(bookingId: String?) {
    val bookingItems = listOf(
        BookingItem("1", "Blue Skies Parking", "Monday, October 24", "8:00 AM - 12:00 PM", "$60", "Confirmed"),
        BookingItem("2", "North Cerulean District", "Saturday, October 22", "8:00 AM - 7:00 PM", "$24", "Completed"),
        BookingItem("3", "Splitter Garage", "Friday, October 21", "8:00 AM - 4:00 PM", "$45", "Cancelled"),
        BookingItem("4", "Park It Down", "Wednesday, October 19", "8:00 AM - 12:00 PM", "$34", "Completed"),
        BookingItem("5", "Jester Park", "Tuesday, October 18", "8:00 AM - 11:00 AM", "$64", "Confirmed"),
        BookingItem("6", "Jester Park", "Tuesday, October 18", "8:00 AM - 11:00 AM", "$64", "Cancelled"),
        BookingItem("7", "Jester Park", "Tuesday, October 18", "8:00 AM - 11:00 AM", "$64", "Confirmed"),
        BookingItem("8", "Jester Park", "Tuesday, October 18", "8:00 AM - 11:00 AM", "$64", "Cancelled"),
        BookingItem("9", "Jester Park", "Tuesday, October 18", "8:00 AM - 11:00 AM", "$64", "Confirmed"),
        BookingItem("10", "Jester Park", "Tuesday, October 18", "8:00 AM - 11:00 AM", "$64", "Cancelled"),
        BookingItem("11", "Jester Park", "Tuesday, October 18", "8:00 AM - 11:00 AM", "$64", "Confirmed"),
        BookingItem("12", "Jester Park", "Tuesday, October 18", "8:00 AM - 11:00 AM", "$64", "Cancelled"),
        BookingItem("13", "Jester Park", "Tuesday, October 18", "8:00 AM - 11:00 AM", "$64", "Confirmed")
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
            Spacer(modifier = Modifier.weight(1f))
            ActionButtons()
        }
    }
}

@Composable
fun BookingInfoSection(booking : BookingItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background), // Replace with your image resource
            contentDescription = "Parking Image",
            modifier = Modifier
                .size(78.dp)
                .clip(RoundedCornerShape(12.dp))
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = booking.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp)) // Space between title and badge
            BookingStatusBadge(booking.status)
        }
        CallButton()
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = "Address",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Airway Boulevard San Francisco, California",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MapSection()
    }
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 16.dp),
        thickness = 1.dp,
        color = Color.LightGray
    )
}

@Composable
fun CallButton() {
    IconButton(
        onClick = { /* TODO: handle call action */ },
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Icon(
            imageVector = Icons.Default.Phone,
            contentDescription = "Call",
            tint = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}

@Composable
fun BookingStatusBadge(status: String) {
    val backgroundColor = when (status) {
        "Confirmed" -> Color(0xFF4CAF50)
        "Cancelled" -> Color(0xFFF44336)
        else -> Color(0xFF616161)
    }
    val textColor = Color(0xFFFFFFFF)

    Badge(
        containerColor  = backgroundColor,
        contentColor = Color.Transparent
    ) {
        Text(
            text = status,
            color = textColor,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(4.dp)
        )
    }
}

@Composable
fun BookingDateAndTime(booking : BookingItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 8.dp)
    ) {
        Text(
            text = "Booking Date and Time",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Normal
        )
        Text(
            text = booking.date,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = booking.time,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun MapSection() {
    // This is a placeholder for a map image, replace with actual implementation if needed
    Image(
        painter = painterResource(id = R.drawable.ic_launcher_background), // Replace with your map image resource
        contentDescription = "Map",
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.Gray), // Use an aspect ratio of 1 to make it a square
        contentScale = ContentScale.Crop // Crop the image if necessary
    )
}

@Composable
fun PriceSection(booking : BookingItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 8.dp)
    ) {
        Text(
            text = "Price",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Normal
        )
        Text(
            text = booking.price,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "for 4h 0m",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun ActionButtons() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { /* TODO: Handle cancel action */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(25), // Fully rounded corners
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red
            )
        ) {
            Text("Cancel", style = MaterialTheme.typography.titleMedium, color = Color.White, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(8.dp)) // Space between the buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = { /* TODO: Handle view timer action */ },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                shape = RoundedCornerShape(25),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFF6200EE) // Assume the border color you want
                ),
                border = BorderStroke(1.dp, Color(0xFF6200EE))
            ) {
                Text("View Timer", style = MaterialTheme.typography.titleMedium, color = Color.Black, fontWeight = FontWeight.Bold)
            }
            Button(
                onClick = { /* TODO: Handle view ticket action */ },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                shape = RoundedCornerShape(20),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
            ) {
                Text("View Ticket", style = MaterialTheme.typography.titleMedium, color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailedBookingScreenPreview() {
    MaterialTheme {
        ActiveBookingScreen("1")
    }
}
