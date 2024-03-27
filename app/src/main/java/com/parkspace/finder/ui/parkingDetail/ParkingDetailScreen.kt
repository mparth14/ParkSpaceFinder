package com.parkspace.finder.ui.parkingDetail

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.widget.Toast
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
import androidx.navigation.NavHostController
import com.parkspace.finder.R
import com.parkspace.finder.data.ParkingSpace
import com.parkspace.finder.data.ParkingSpaceRepository
import com.parkspace.finder.data.ParkingSpaceViewModel
import com.parkspace.finder.ui.browse.Rating
import kotlinx.coroutines.launch
import java.util.Locale

//@Composable
//fun ParkingDetailScreen(navController: NavHostController) {
//    Text(text = "Parking Detail Screen")
//}

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

@Composable
fun AddressBar(context: Context, parkingSpace: ParkingSpace) {
    val address = getAddressFromLocation(context, parkingSpace.location.latitude, parkingSpace.location.longitude)
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
private fun getAddressFromLocation(context: Context, latitude: Double, longitude: Double): String? {
    val geocoder = Geocoder(context, Locale.getDefault())
    return try {
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            val address = addresses?.get(0)
            "${address?.getAddressLine(0)}, ${address?.locality}, ${address?.adminArea}, ${address?.countryName}"

    } catch (e: Exception) {
        // Handle any exceptions
        Toast.makeText(context, "Error fetching address: ${e.message}", Toast.LENGTH_SHORT).show()
        null
    }
}
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ParkingDetailScreen(
    navController: NavHostController,
    parkingSpaceName: String,
    viewModel: ParkingSpaceViewModel,
    parkingSpaceRepository: ParkingSpaceRepository
) {
    var parkingSpace by remember { mutableStateOf<ParkingSpace?>(null) }
    val coroutineScope = rememberCoroutineScope()
    var isFavorite by remember { mutableStateOf(false) }
    val locationName = remember { mutableStateOf("Loading...") }

    LaunchedEffect(key1 = parkingSpaceName) {
        // Fetch parking space details when the parkingSpaceName changes
        coroutineScope.launch {
            try {
                val fetchedParkingSpace = parkingSpaceRepository.getParkingSpaceByName(parkingSpaceName)
                parkingSpace = fetchedParkingSpace
            } catch (e: Exception) {
                // Handle error
            }
//            try {
//                val fetchedParkingSpace = parkingSpaceRepository.getParkingSpaceByName(parkingSpaceName)
//                parkingSpace = fetchedParkingSpace
//                // Get the address based on latitude and longitude
//                fetchedParkingSpace?.let { space ->
//                    locationName.value = getLocationName(space.location.latitude, space.location.longitude)
//                }
//            } catch (e: Exception) {
//                // Handle error
//            }
        }
    }
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
                    parkingSpace?.let { space ->
                        IconButton(onClick = {
                            // Toggle favorite state
                            isFavorite = !isFavorite
                        }) {
                            val icon = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder
                            Icon(
                                imageVector = icon,
                                contentDescription = "Add to favorites"
                            )
                        }
                    }
                }
            )
        },
        content = {
            // Parking details content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                parkingSpace?.let { space ->
//                    Text(
//                        text = "${space.location}",
//                        fontSize = 12.sp
//                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = space.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    // Display hourly rate
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "$${space.hourlyPrice}/hr",
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
//                    Spacer(modifier = Modifier.height(16.dp))
//                    Row(verticalAlignment = Alignment.CenterVertically) {
//                        Row(verticalAlignment = Alignment.CenterVertically) {
//                            Spacer(modifier = Modifier.width(16.dp))
//                            Icon(
//                                painter = painterResource(id = R.drawable.location_24), // Your location icon resource
//                                contentDescription = "Location",
//                                tint = Color(0xFF777777),
//                                modifier = Modifier.size(16.dp)
//                            )
//                            Text(
//                                text = "${String.format("%.2f", space.distanceFromCurrentLocation)} km away",
//                                style = MaterialTheme.typography.bodySmall,
//                                color = Color(0xFF777777),
//                                modifier = Modifier.padding(start = 4.dp) // Add padding to separate the icon and text
//                            )
//                        }
//                        Spacer(modifier = Modifier.width(32.dp)) // Add space between the first and second items
//                        // Add the second item: "Available"
//                        Text(
//                            text = "Available",
//                            style = MaterialTheme.typography.bodySmall,
//                            color = Color(0xFF777777)
//                        )
//                    }
//                    Card(
//                        modifier = Modifier
//                            .padding(vertical = 8.dp)
//                            .height(200.dp)
//                            .width(500.dp),
//                        shape = MaterialTheme.shapes.medium,
//                    ) {
//                        Image(
//                            painter = rememberAsyncImagePainter(space.imageURL),
//                            contentDescription = "Crosswalk Lot",
//                            modifier = Modifier.fillMaxSize(),
//                        )
//                    }
//                    Spacer(modifier = Modifier.height(16.dp))
//                    // Facilities section
//                    Text(
//                        text = "Facilities",
//                        fontWeight = FontWeight.Bold,
//                        fontSize = 20.sp
//                    )
//                    Spacer(modifier = Modifier.height(8.dp))
//                    // Display facilities in rounded rectangles
//                    Row {
//                        FacilityItem(name = "CCTV")
//                        Spacer(modifier = Modifier.width(8.dp))
//                        FacilityItem(name = "Hydraulic Parking")
//                        Spacer(modifier = Modifier.width(8.dp))
//                        FacilityItem(name = "Security")
//                    }
//                    Spacer(modifier = Modifier.height(8.dp))
//                    Row {
//                        FacilityItem(name = "Automated Tickets")
//                        Spacer(modifier = Modifier.width(8.dp))
//                        FacilityItem(name = "Parking Assistance")
//                    }
//                    Spacer(modifier = Modifier.height(8.dp))
//                    // Working Hours section
//                    Text(
//                        text = "Working Hours",
//                        fontWeight = FontWeight.Bold,
//                        fontSize = 20.sp,
//                        //modifier = Modifier.padding(vertical = 8.dp)
//                    )
//                    Column(
//                        modifier = Modifier
//                            .padding(bottom = 8.dp)
//                            .background(color = Color.White, shape = MaterialTheme.shapes.medium)
//                            .padding(8.dp)
//                    ) {
//                        // Display working hours
//                        listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday").forEach { day ->
//                            Row(
//                                modifier = Modifier.padding(vertical = 4.dp),
//                                verticalAlignment = Alignment.CenterVertically
//                            ) {
//                                Text(
//                                    text = day,
//                                    color = Color.DarkGray, // Adjust color for days
//                                    modifier = Modifier.weight(1f)
//                                )
//                                Text(
//                                    text = "8am - 8pm",
//                                    modifier = Modifier.weight(1f)
//                                )
//                            }
//                        }
//                    }
//                    Text(
//                        text = "Description",
//                        fontWeight = FontWeight.Bold,
//                        fontSize = 20.sp,
//                        //modifier = Modifier.padding(vertical = 8.dp)
//                    )
//                    Text(
//                        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce quis mauris vel odio ultricies condimentum. Nullam efficitur quam at est blandit, ac bibendum nulla sagittis. Mauris id quam et velit fermentum consectetur.",
//                        modifier = Modifier.padding(top = 8.dp)
//                    )
                    Text(
                        text = "Location",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    AddressBar(context = LocalContext.current, parkingSpace = space)
                    Button(
                        onClick = { navController.navigate("ROUTE_BOOKINGS") },
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        //colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                    ) {
                        Text(text = "Book Now")
                    }
                }
            }
        }
    )
}
