package com.parkspace.finder.ui.parkingticket

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Directions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.parkspace.finder.data.Resource
import com.parkspace.finder.navigation.ROUTE_BROWSE
import com.parkspace.finder.viewmodel.BookingDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParkingTicketScreen(navController: NavController, bookingId: String) {
    val bookingDetailViewModel =
        hiltViewModel<BookingDetailViewModel, BookingDetailViewModel.Factory> {
            it.create(bookingId)
        }
    val bookingDetail = bookingDetailViewModel.bookingDetail.collectAsState()
    val qrCodeBitmap = bookingDetailViewModel.qrCodeBitmap.collectAsState()
    val bookedParkingSpace = bookingDetailViewModel.bookedParkingSpace.collectAsState()
    val context = LocalContext.current
    when (bookingDetail.value) {
        is Resource.Success -> {
            val details = (bookingDetail.value as Resource.Success).result
            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(
                                text = "Parking Ticket",
                                fontWeight = FontWeight.Bold
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                            }
                        },
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = MaterialTheme.colorScheme.background,
                            titleContentColor = MaterialTheme.colorScheme.onBackground
                        )
                    )
                },
                content = { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                            .background(Color.Transparent)
                            .padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            shape = RoundedCornerShape(16.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White,
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "Scan QR Code to Enter",
                                    style = MaterialTheme.typography.bodyLarge,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(horizontal = 24.dp)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                qrCodeBitmap.value?.let { Image(
                                    bitmap = qrCodeBitmap.value!!, contentDescription = "QR Code"
                                )
                                } ?: CircularProgressIndicator() // Show a loader while the QR code is null
                                HorizontalDivider(
                                    modifier = Modifier.padding(vertical = 16.dp),
                                    thickness = 1.dp,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                                )
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    TimeSlot(title = "Start", time = details.startTime)
                                    TimeSlot(title = "End", time = details.endTime)
                                }
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text("The Floor", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                                        Text("1st floor") // Use actual data or state variable
                                    }
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text("Spot", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                                        Text(details.spotNumber)
                                    }
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text("Duration", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                                        Text("2 hours") // Use actual data or state variable
                                    }
                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    OutlinedButton(
                                        onClick = {
                                            val qrData =
                                                "Start Time: ${details.startTime}\n" + "End Time: ${details.endTime}\n" + "Spot Number: ${details.spotNumber}\n" + "Price: ${details.price}\n" + "Lot id: ${details.lotId}\n" + "Booking id: ${details.id}\n"
                                            qrCodeBitmap.value?.let { it1 ->
                                                shareTicketInfo(
                                                    qrData, it1.asAndroidBitmap(), context
                                                )
                                            }
                                        },
                                        modifier = Modifier.weight(1f),
                                        shape = RoundedCornerShape(25),
                                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                                        colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.primary)
                                    ) {
                                        Icon(Icons.Filled.Share, contentDescription = "Share")
                                        Text(" Share")
                                    }
                                    Spacer(modifier = Modifier.width(16.dp))
                                    when (bookedParkingSpace.value) {
                                        is Resource.Success -> {
                                            val space =
                                                (bookedParkingSpace.value as Resource.Success).result
                                            OutlinedButton(
                                                onClick = {
                                                    val latitude = space?.location?.latitude
                                                    val longitude = space?.location?.longitude
                                                    val gmmIntentUri =
                                                        Uri.parse("google.navigation:q=$latitude,$longitude")
                                                    val mapIntent =
                                                        Intent(Intent.ACTION_VIEW, gmmIntentUri)
                                                    mapIntent.setPackage("com.google.android.apps.maps")
                                                    context.startActivity(mapIntent)
                                                },
                                                modifier = Modifier.weight(1f),
                                                shape = RoundedCornerShape(25),
                                                border = BorderStroke(
                                                    1.dp,
                                                    MaterialTheme.colorScheme.primary
                                                ),
                                                colors = ButtonDefaults.outlinedButtonColors(
                                                    contentColor = MaterialTheme.colorScheme.primary
                                                )
                                            ) {
                                                Icon(
                                                    Icons.Filled.Directions,
                                                    contentDescription = "Directions"
                                                )
                                                Text(" Directions")
                                            }
                                        }

                                        else -> {
                                            OutlinedButton(
                                                onClick = { /* TODO: Implement download functionality */ },
                                                modifier = Modifier.weight(1f),
                                                shape = RoundedCornerShape(25),
                                                border = BorderStroke(
                                                    1.dp,
                                                    MaterialTheme.colorScheme.primary
                                                ),
                                                colors = ButtonDefaults.outlinedButtonColors(
                                                    contentColor = MaterialTheme.colorScheme.primary
                                                )
                                            ) {
                                                Icon(
                                                    Icons.Outlined.Directions,
                                                    contentDescription = "Directions"
                                                )
                                                Text(" Directions")
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                        Button(
                            onClick = {
                                    navController.navigate(ROUTE_BROWSE){
                                        popUpTo(ROUTE_BROWSE) {
                                        inclusive = true
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp)
                                .height(50.dp),
                            shape = RoundedCornerShape(25), // Rounded corners for the button
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                        ) {
                            Text("Let's Go To Home", color = Color.White)
                        }
                    }
                }
            )
        }

        is Resource.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is Resource.Failure -> {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                Text("Error")
            }
        }

        else -> {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                Text("Error")
            }
        }
    }

}

@Composable
fun TimeSlot(title: String, time: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
        Text(
            time,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Light),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(4.dp)
        )
    }
}

fun shareTicketInfo(ticketData: String, qrBitmap: Bitmap, context: Context) {
    val resolver: ContentResolver = context.contentResolver
    val qrImageUri = saveQRCodeImage(qrBitmap, context)

    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, ticketData)
        putExtra(Intent.EXTRA_STREAM, qrImageUri)
        type = "image/*"
    }

    val shareIntent = Intent.createChooser(sendIntent, null)
    context.startActivity(shareIntent)
}

fun saveQRCodeImage(bitmap: Bitmap, context: Context): Uri {
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, "QR_Code")
        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
    }

    val resolver: ContentResolver = context.contentResolver
    val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

    resolver.openOutputStream(imageUri!!).use { outputStream ->
        if (outputStream != null) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        }
    }

    return imageUri
}
