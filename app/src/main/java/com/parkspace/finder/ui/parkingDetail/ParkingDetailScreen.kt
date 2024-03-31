package com.parkspace.finder.ui.parkingDetail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.parkspace.finder.R
import com.parkspace.finder.data.ParkingSpace
import com.parkspace.finder.data.Resource
import com.parkspace.finder.navigation.ROUTE_ENTER_BOOKING_DETAIL_SCREEN
import com.parkspace.finder.ui.browse.Rating
import com.parkspace.finder.ui.notification.NotificationUtils
import com.parkspace.finder.ui.notification.scheduleNotification
import com.parkspace.finder.viewmodel.ParkingDetailViewModel
import kotlinx.coroutines.launch
import java.util.Locale
/*
 * This file contains composable functions for displaying details of a parking space.
 */


/**
 * Composable function for displaying a map with a marker at the given location.
 * @param location The latitude and longitude of the location to be marked on the map.
 */
@Composable
fun MapContent(location: LatLng) {
    val context = LocalContext.current
    var mapView: MapView? by remember { mutableStateOf(null) }
    var googleMap: GoogleMap? by remember { mutableStateOf(null) }

    LaunchedEffect(location) {
        val mapViewInstance = MapView(context).apply {
            getMapAsync { map ->
                googleMap = map
                // Add marker to the map
                googleMap?.addMarker(
                    MarkerOptions()
                        .position(location)
                        .title("Parking Space")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                )
            }
        }
        mapViewInstance.onCreate(null)
        launch { mapViewInstance.onResume() }
        mapView = mapViewInstance
    }

    AndroidView({ mapView!! }) { mapView ->
        mapView.getMapAsync { map ->
            googleMap = map
            // Add marker to the map
            googleMap?.addMarker(
                MarkerOptions()
                    .position(location)
                    .title("Parking Space")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
            )
        }
    }
}

/**
 * Composable function for displaying a star rating.
 * @param rating The rating value to be displayed.
 */
@Composable
fun SetReminder(context: Context) {
    // Get the delay before the reminder from your UI or any other source
    val delayBeforeReminderMillis = 20 * 1000 // 10 minutes before the end of the parking session

    // Title and message for the notification
    val title = "Parking Reminder"
    val message = "Your parking session is ending in 10 minutes."
    val CHANNEL_ID = "your_channel_id"


    // Call the function to create the notification channel
    NotificationUtils.createNotificationChannel(context, CHANNEL_ID, "Parking Reminders")

    // Call the scheduleNotification function to trigger the notification
    scheduleNotification(
        context = context,
        delay = delayBeforeReminderMillis,
        notificationId = 1,
        channelId = CHANNEL_ID,
        title = title,
        message = message
    )
}

@Composable
fun Rating(rating: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1..5) {
            androidx.compose.material3.Icon(
                painter = painterResource(id = if (i <= rating) R.drawable.star_filled_24 else R.drawable.star_empty_24),
                contentDescription = "Rating Star",
                tint = if (i <= rating) Color(0xFFFAAF00) else Color(0xFFAAAAAA),
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

/**
 * Composable function for displaying a facility item.
 * @param name The name of the facility.
 */
@Composable
fun FacilityItem(name: String) {
    Surface(
        color = Color.LightGray.copy(alpha = 0.4f),
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.padding(4.dp)
    ) {
        Text(
            text = name,
            modifier = Modifier.padding(8.dp),
            fontWeight = FontWeight.Bold
        )
    }
}

/**
 * Composable function for displaying the address bar with a location icon.
 * @param context The context used to fetch the address.
 * @param parkingSpace The parking space object containing location information.
 */
@Composable
fun AddressBar(context: Context, parkingSpace: ParkingSpace) {
    val address = getAddressFromLocation(
        context,
        parkingSpace.location.latitude,
        parkingSpace.location.longitude
    )
    Row {
        Icon(
            painter = painterResource(id = R.drawable.location_24),
            contentDescription = "Location icon",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .size(32.dp)
                .padding(end = 8.dp)
        )
        Column {
            address?.let {
                Text(
                    text = it,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

/**
 * Function to fetch the address from a location using geocoding.
 * @param context The context used to fetch the address.
 * @param latitude The latitude of the location.
 * @param longitude The longitude of the location.
 * @return The address as a string if successfully fetched, null otherwise.
 */
private fun getAddressFromLocation(context: Context, latitude: Double, longitude: Double): String? {
    val geocoder = Geocoder(context, Locale.getDefault())
    return try {
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)
        val address = addresses?.get(0)
        Log.d("Addresses", latitude.toString() + " " +  longitude.toString())
        Log.d("Address", address.toString())
        "${address?.getAddressLine(0)}, ${address?.locality}, ${address?.adminArea}, ${address?.countryName}"
    } catch (e: Exception) {
        // Handle any exceptions
        Toast.makeText(context, "Error fetching address: ${e.message}", Toast.LENGTH_SHORT).show()
        null
    }
}

/**
 * Composable function for displaying the screen for parking details.
 * @param navController The navigation controller used for navigation.
 * @param parkingId The ID of the parking space.
 */
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ParkingDetailScreen(
    navController: NavHostController,
    parkingId: String,
) {
    val parkingDetailViewModel: ParkingDetailViewModel =
        hiltViewModel<ParkingDetailViewModel, ParkingDetailViewModel.Factory> {
            it.create(parkingId)
        }
    val parkingSpace = parkingDetailViewModel.parkingSpace.collectAsState()
    val scrollState = rememberScrollState()
    val context  = LocalContext.current
    SetReminder(context)
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Parking Details",
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    when (parkingSpace.value) {
                        is Resource.Success -> {
                            val space = (parkingSpace.value as Resource.Success).result
                            val isFavorite = false;
                            IconButton(onClick = {
                                //parkingDetailViewModel.toggleFavorite()
                            }) {
                                Icon(
                                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                    contentDescription = "Favorite"
                                )
                            }
                        }

                        else -> {}
                    }
                }
            )
        },
        content = {
            // Parking details content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                when (parkingSpace.value){
                    is Resource.Success -> {
                        val space = (parkingSpace.value as Resource.Success).result
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = space?.name ?: "",
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        // Display hourly rate
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "$${space?.hourlyPrice}/hr",
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            // Display rating stars and rating value
                            Rating(rating = 4)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "23",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {

                            Text(
                                text = "Available",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF777777)
                            )
                        }
                        Card(
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .height(200.dp)
                                .width(500.dp),
                            shape = MaterialTheme.shapes.medium,
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(space?.imageURL),
                                contentDescription = "Crosswalk Lot",
                                modifier = Modifier.fillMaxSize(),
                            )
                        }
                        Button(
                            onClick = { navController.navigate(ROUTE_ENTER_BOOKING_DETAIL_SCREEN.replace("{parkingId}", parkingId))},
                            modifier = Modifier
                                .fillMaxWidth(),
                            //colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                        ) {
                            Text(text = "Book Now", color = Color.White)
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        // Facilities section
                        Text(
                            text = "Facilities",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        // Display facilities in rounded rectangles
                        Row {
                            FacilityItem(name = "CCTV")
                            Spacer(modifier = Modifier.width(8.dp))
                            FacilityItem(name = "Hydraulic Parking")
                            Spacer(modifier = Modifier.width(8.dp))
                            FacilityItem(name = "Security")
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row {
                            FacilityItem(name = "Automated Tickets")
                            Spacer(modifier = Modifier.width(8.dp))
                            FacilityItem(name = "Parking Assistance")
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Location",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        AddressBar(context = LocalContext.current, parkingSpace = space!!)
                        Button (
                            onClick = {
                                var gmmIntentUri = "google.navigation:q=${space.location.latitude},${space.location.longitude}"
                                var mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse(gmmIntentUri))
                                mapIntent.setPackage("com.google.android.apps.maps")
                                context.startActivity(mapIntent)
                            },
                            modifier = Modifier
                                .fillMaxWidth(),
                        ) {
                            Text(text = "Get Directions", color = Color.White)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        // Working Hours section
                        Text(
                            text = "Working Hours",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            //modifier = Modifier.padding(vertical = 8.dp)
                        )
                        Column(
                            modifier = Modifier
                                .padding(bottom = 8.dp)
                                .background(
                                    color = Color.White,
                                    shape = MaterialTheme.shapes.medium
                                )
                                .padding(8.dp)
                        ) {
                            // Display working hours
                            listOf(
                                "Monday",
                                "Tuesday",
                                "Wednesday",
                                "Thursday",
                                "Friday",
                                "Saturday",
                                "Sunday"
                            ).forEach { day ->
                                Row(
                                    modifier = Modifier.padding(vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = day,
                                        color = Color.DarkGray, // Adjust color for days
                                        modifier = Modifier.weight(1f)
                                    )
                                    Text(
                                        text = "8am - 8pm",
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            }
                        }
                        Text(
                            text = "Description",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            //modifier = Modifier.padding(vertical = 8.dp)
                        )
                        Text(
                            text = "This parking space offers convenient and secure parking facilities for both short-term and long-term stays. With state-of-the-art security measures and easy access, it's an ideal choice for commuters and visitors alike.",
                            modifier = Modifier.padding(top = 8.dp)
                        )


                    }
                    is Resource.Loading -> {
                        CircularProgressIndicator()
                    }
                    is Resource.Failure -> {
                        Text(text = "Error loading parking space details")
                    }
                    else -> {
                        Text(text = "UNKNOWN ERROR")
                    }
                }
            }
        }
    )
}