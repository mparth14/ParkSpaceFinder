package com.parkspace.finder.ui.parkingticket

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.parkspace.finder.data.BookingViewModel
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
            var details = (bookingDetail.value as Resource.Success).result
            Scaffold(topBar = {
                TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black,
                    navigationIconContentColor = Color.Black,
                    actionIconContentColor = Color.Black
                ), title = {
                    Text(
                        text = "Parking Ticket",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 30.dp)
                    )
                }, navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                })
            }, content = {
                Column(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Scan QR Code to Enter",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Box(
                        modifier = Modifier
                            .size(350.dp)
                            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                            .padding(16.dp), contentAlignment = Alignment.Center
                    ) {
                        Image(
                            bitmap = qrCodeBitmap.value!!, contentDescription = "QR Code"
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Start Time",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = details.startTime,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "End Time",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = details.endTime,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Spot Number",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = details.spotNumber,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Price",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "${details.price}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Parking Lot",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold
                            )
                            when (bookedParkingSpace.value) {
                                is Resource.Success -> {
                                    val parkingSpace =
                                        (bookedParkingSpace.value as Resource.Success).result
                                    Log.d("ParkingTicket", "Parking Space: ${parkingSpace}")
                                    Text(
                                        text = parkingSpace?.name ?: "",
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                }

                                else -> {
                                    Text(
                                        text = "Loading...",
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                }
                            }
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(onClick = {
                            val qrData =
                                "Start Time: ${details.startTime}\n" + "End Time: ${details.endTime}\n" + "Spot Number: ${details.spotNumber}\n" + "Price: ${details.price}\n" + "Lot id: ${details.lotId}\n" + "Booking id: ${details.id}\n"
                            qrCodeBitmap.value?.let { it1 ->
                                shareTicketInfo(
                                    qrData, it1.asAndroidBitmap(), context
                                )
                            }
                        }) {
                            Text("Share")
                        }
                        when (bookedParkingSpace.value) {
                            is Resource.Success -> {
                                val space = (bookedParkingSpace.value as Resource.Success).result
                                Button(onClick = {
                                    val latitude = space?.location?.latitude
                                    val longitude = space?.location?.longitude
                                    val gmmIntentUri =
                                        Uri.parse("google.navigation:q=$latitude,$longitude")
                                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                                    mapIntent.setPackage("com.google.android.apps.maps")
                                    context.startActivity(mapIntent)
                                }) {
                                    Text("Open in Maps")
                                }
                            }

                            else -> {
                                Button(
                                    onClick = {}, enabled = false
                                ) {
                                    Text("Open in Maps")
                                }
                            }
                        }
                    }
                    Button(onClick = {
                        navController.navigate(ROUTE_BROWSE){
                            popUpTo(ROUTE_BROWSE) {
                                inclusive = true
                            }
                        }
                    }, modifier = Modifier.padding(16.dp).fillMaxWidth()) {
                        Text(text = "Go to home screen")
                    }
                }
            })
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
