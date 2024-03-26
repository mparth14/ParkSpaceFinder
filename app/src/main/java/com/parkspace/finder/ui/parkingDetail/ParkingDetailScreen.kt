package com.parkspace.finder.ui.parkingDetail

import android.annotation.SuppressLint
import android.location.Geocoder
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import com.parkspace.finder.data.ParkingSpaceViewModel
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.parkspace.finder.data.ParkingSpace
import com.parkspace.finder.data.ParkingSpaceRepository
import kotlinx.coroutines.launch
import java.util.Locale

//@Composable
//fun ParkingDetailScreen(navController: NavHostController) {
//    Text(text = "Parking Detail Screen")
//}

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


    LaunchedEffect(key1 = parkingSpaceName) {
        // Fetch parking space details when the parkingSpaceName changes
        coroutineScope.launch {
            try {
                val fetchedParkingSpace = parkingSpaceRepository.getParkingSpaceByName(parkingSpaceName)
                parkingSpace = fetchedParkingSpace
            } catch (e: Exception) {
                // Handle error
            }
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
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                parkingSpace?.let { space ->
                    // Display location name above parking space name
                    val locationName = getLocationName( .geocoder, space.location.latitude, space.location.longitude)
                    Text(text = locationName, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.height(8.dp))
                    // Display parking space name
                    Text(text = "Parking Space Name: ${space.name}", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))
                    // Display other details of the parking space
                    // For example:
                    Text(text = "Hourly Price: ${space.hourlyPrice}")
                    Text(text = "Location: ${space.location}")
                    // Add more details as needed
                }
            }
        }
    )
}
@Composable
fun getLocationName(latitude: Double, longitude: Double): String {
    return try {
        val geocoder = Geocoder(LocalContext.current, Locale.getDefault())
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)
        addresses?.firstOrNull()?.getAddressLine(0) ?: "Unknown Location"
    } catch (e: Exception) {
        Log.e("ParkingDetailScreen", "Error getting location name: ${e.message}")
        "Unknown Location"
    }
}
