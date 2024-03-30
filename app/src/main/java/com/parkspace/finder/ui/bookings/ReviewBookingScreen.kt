package com.parkspace.finder.ui.bookings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ReviewBookingScreen(
    bookingDetails: BookingDetails,
    onConfirmClick: () -> Unit
) {
    Scaffold(
        content = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                item {
                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )
                    Text(
                        text = "Review Booking",
                        fontSize = 40.sp
                    )
                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )
                }
                item {
                    Text(
                        text = "Date & Time:",
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = bookingDetails.date,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = bookingDetails.time,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }

                item {
                    Text(
                        text = "Parking Space:",
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = bookingDetails.parkingSpace,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = bookingDetails.vehicleType,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }
                item {
                    Text(
                        text = "Address:",
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = bookingDetails.address,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Text(
                        text = bookingDetails.distance,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }
                item {
                    Text(
                        text = "Payment Method:",
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = bookingDetails.paymentMethod,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }
                item {
                    Text(
                        text = "Payment Details:",
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = bookingDetails.vehicleType,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = bookingDetails.vehicleType,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = bookingDetails.paymentAmount,
                            fontSize = 10.sp,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = bookingDetails.vehicleType,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = bookingDetails.paymentAmount,
                            fontSize = 10.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
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
                        Text(
                            text = bookingDetails.paymentTotal,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Code Redeemed:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(bottom = 8.dp, start = 16.dp)
                        )
                        Box(
                            modifier = Modifier
                                .padding(end = 16.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(color = Color(0xFFFFA500), shape = RoundedCornerShape(8.dp))
                            ) {
                                Text(
                                    text = bookingDetails.redeemedCode,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
                item {
                    Button(
                        onClick = onConfirmClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 32.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF7B99A9))
                    ) {
                        Text(
                            text = "Confirm",
                            fontSize = 20.sp)
                    }
                }
            }
        }
    )
}

@Preview
@Composable
fun PreviewBookingDetailsScreen() {
    ReviewBookingScreen(
        bookingDetails = BookingDetails(
            date = "March 16, 2024",
            time = "10:00 AM",
            parkingSpace = "Parking Space A",
            vehicleType = "Hatchback Sedan",
            address = "123 Main Street, City, Country",
            distance = "0.31 mi",
            paymentMethod = "Credit Card",
            paymentAmount = "$100",
            paymentTotal = "$100",
            redeemedCode = "20% off"
        ),
        onConfirmClick = {}
    )
}

data class BookingDetails(
    val date: String,
    val time: String,
    val parkingSpace: String,
    val vehicleType: String,
    val address: String,
    val distance: String,
    val paymentMethod: String,
    val paymentAmount: String,
    val paymentTotal: String,
    val redeemedCode: String
)