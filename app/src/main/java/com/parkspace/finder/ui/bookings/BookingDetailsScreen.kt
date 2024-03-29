package com.parkspace.finder.ui.bookings

import android.content.Intent
import android.net.Uri
import android.util.Log
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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.GeoPoint
import com.parkspace.finder.R
import com.parkspace.finder.data.BookingDetails
import com.parkspace.finder.data.ParkingSpace
import com.parkspace.finder.data.Resource
import com.parkspace.finder.data.utils.calculateDuration
import com.parkspace.finder.data.utils.getAddressesFromLatLng
import com.parkspace.finder.navigation.ROUTE_PARKING_TICKET
import com.parkspace.finder.viewmodel.BookingDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingDetailsScreen(navController: NavHostController, bookingId: String) {

    Log.d("BookingDetailsScreen", "Booking ID: $bookingId")
    val bookingDetailViewModel = hiltViewModel<BookingDetailViewModel, BookingDetailViewModel.Factory>{
        it.create(bookingId)
    }

    val bookingDetails = bookingDetailViewModel.bookingDetail.collectAsState()
    val parkingSpace = bookingDetailViewModel.bookedParkingSpace.collectAsState()
    when (bookingDetails.value) {
        is Resource.Success -> {
            when (parkingSpace.value) {
                is Resource.Success -> {
                    val booking = (bookingDetails.value as Resource.Success).result
                    val space = (parkingSpace.value as Resource.Success).result
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = { Text("Booking Details") },
                                navigationIcon = {
                                    IconButton(onClick = { navController.popBackStack() }) {
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
                            BookingInfoSection(booking, space)
                            Spacer(modifier = Modifier.height(1.dp))
                            BookingDateAndTime(booking)
                            Spacer(modifier = Modifier.height(1.dp))
                            PriceSection(booking)
                            Spacer(modifier = Modifier.weight(1f))
                            ActionButtons(navController, bookingId)
                        }
                    }
                }
                is Resource.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is Resource.Failure -> {
                    // Show an error message
                }
                else -> {
                    // Show an empty state
                }
            }
        }
        is Resource.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is Resource.Failure -> {
            // Show an error message
        }
        else -> {
            // Show an empty state

        }
    }
}

@Composable
fun BookingInfoSection(booking : BookingDetails, parkingSpace: ParkingSpace?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = parkingSpace?.imageURL), // Replace with your image resource
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
                text = parkingSpace?.name ?:"",
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
        val context = LocalContext.current
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
                text = getAddressesFromLatLng(context, parkingSpace?.location ?: GeoPoint(0.0, 0.0)),
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
        MapSection(parkingSpace = parkingSpace)
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
fun BookingDateAndTime(booking : BookingDetails) {
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
            text = booking.bookingDate,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = booking.startTime + " - " + booking.endTime,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun MapSection(parkingSpace: ParkingSpace?) {
    val context = LocalContext.current
    Button(onClick = {
        val gmmIntentUri =
            Uri.parse("google.navigation:q=${parkingSpace?.location?.latitude},${parkingSpace?.location?.longitude}")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        context.startActivity(mapIntent)
    }) {
        Text("Get Directions")
    }
}

@Composable
fun PriceSection(booking : BookingDetails) {
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
            text = booking.price.toString(),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = calculateDuration(booking.startTime, booking.endTime),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun ActionButtons(navController: NavHostController, bookingId: String) {
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
                onClick = { navController.navigate(ROUTE_PARKING_TICKET.replace("{bookingId}", bookingId)) },
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