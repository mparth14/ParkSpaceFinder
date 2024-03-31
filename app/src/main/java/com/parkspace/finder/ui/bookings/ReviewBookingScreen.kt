package com.parkspace.finder.ui.bookings

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.google.firebase.firestore.GeoPoint
import com.parkspace.finder.data.BookingViewModel
import com.parkspace.finder.data.ParkingSpace
import com.parkspace.finder.data.Resource
import com.parkspace.finder.data.utils.calculateDurationInHours
import com.parkspace.finder.data.utils.formatTime
import com.parkspace.finder.data.utils.getAddressesFromLatLng
import com.parkspace.finder.ui.booking.BookingHeader


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ReviewBookingScreen(
    navController: NavHostController,
    parkingId: String,
    backStackEntry: NavBackStackEntry
) {
    val context = LocalContext.current
    val myBackStackEntry = remember(backStackEntry) {
        navController.getBackStackEntry("parking/${parkingId}/book")
    }
    val bookingViewModel: BookingViewModel = hiltViewModel(myBackStackEntry)
    val parkingSpace = bookingViewModel.parkingSpace.collectAsState()
    val startTime = bookingViewModel.startTimeSelection.collectAsState()
    val endTime = bookingViewModel.endTimeSelection.collectAsState()
    val bookingDate = bookingViewModel.dateSelection.collectAsState()
    val vehicleType = bookingViewModel.vehicleSelection.collectAsState()
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .padding(4.dp)
        ) {
            BookingHeader(navController = navController, headerText = "Review Booking")
            Spacer(
                modifier = Modifier.height(12.dp)
            )
            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth()

            ) {
                Column {
                    Text(
                        text = "Date & Time:",
                        modifier = Modifier.padding(bottom = 8.dp),
                    )
                    Text(
                        text = bookingDate.value.toString(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = formatTime(startTime.value),
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }

                Column {
                    Text(
                        text = "Parking Space:",
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    when (parkingSpace.value) {
                        is Resource.Success -> {
                            val space =
                                (parkingSpace.value as Resource.Success<ParkingSpace?>).result
                            Text(
                                text = space?.name ?: "",
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                        }

                        is Resource.Loading -> {
                            Text(text = "Loading...")
                        }

                        is Resource.Failure -> {
                            Text(text = "Error")
                        }

                        else -> {
                            Text(text = "Error")
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = vehicleType.value,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }
                Column {
                    Text(
                        text = "Address:",
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    when (parkingSpace.value) {
                        is Resource.Success -> {
                            val space =
                                (parkingSpace.value as Resource.Success<ParkingSpace?>).result
                            Text(
                                text = getAddressesFromLatLng(
                                    context,
                                    space?.location ?: GeoPoint(0.0, 0.0)
                                ),
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                            Text(
                                text = space?.distanceFromCurrentLocation.toString() + " km away",
                                fontWeight = FontWeight.Bold
                            )
                        }

                        is Resource.Loading -> {
                            Text(text = "Loading...")
                        }

                        is Resource.Failure -> {
                            Text(text = "Error")
                        }

                        else -> {
                            Text(text = "Error")
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }
                Column {
                    Text(
                        text = "Payment Method:",
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Credit Card",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }
                Column {
                    Text(
                        text = "Payment Details:",
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Total",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        when (parkingSpace.value) {
                            is Resource.Success -> {
                                val space =
                                    (parkingSpace.value as Resource.Success<ParkingSpace?>).result
                                Text(
                                    text = "$ %.2f".format(
                                        calculateDurationInHours(
                                            formatTime(startTime.value),
                                            formatTime(endTime.value)
                                        ) * (space?.hourlyPrice ?: 0.0)
                                    ),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                            }

                            is Resource.Loading -> {
                                Text(text = "Loading...")
                            }

                            is Resource.Failure -> {
                                Text(text = "Error")
                            }

                            else -> {
                                Text(text = "Error")
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
                Column {
                    Button(
                        onClick = {
                            navController.navigate("parking/${parkingId}/pay")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 32.dp),
                    ) {
                        Text(
                            text = "Proceed to payment",
                        )
                    }
                }
            }
        }
    }
}