package com.parkspace.finder.ui.parkingticket

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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParkingTicketScreen(
    startTime: String,
    endTime: String,
    spotNumber: String,
    duration: String,
    price: Double,
    lotName: String,
    onBackClicked: () -> Unit
) {
    val context = LocalContext.current
    val priceUpdated = price.toFloat();
    val qrData = "Start Time: $startTime\n" +
            "End Time: $endTime\n" +
            "Spot Number: $spotNumber\n" +
            "Duration: $duration\n" +
            "Price: $priceUpdated\n" +
            "Lot Name: $lotName"
    val qrCodeBitmap by remember { mutableStateOf(generateQRCodeBitmap(qrData, context)) }



    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black,
                    navigationIconContentColor = Color.Black,
                    actionIconContentColor = Color.Black
                ),
                title = {
                    Text(
                        text = "Parking Ticket",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 30.dp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onBackClicked() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(text = "Scan QR Code to Enter", style = MaterialTheme.typography.headlineMedium)
                Box(
                    modifier = Modifier
                        .size(400.dp)
                        .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        bitmap = qrCodeBitmap,
                        contentDescription = "QR Code"
                    )
                }
                Text(text = "Start Time: $startTime", style = MaterialTheme.typography.bodyLarge)
                Text(text = "End Time: $endTime", style = MaterialTheme.typography.bodyLarge)
                Text(text = "Spot Number: $spotNumber", style = MaterialTheme.typography.bodyLarge)
                Text(text = "Duration: $duration", style = MaterialTheme.typography.bodyLarge)
                Text(text = "Price: $price"  , style = MaterialTheme.typography.bodyLarge)
                Text(text = "Parking Lot: $lotName", style = MaterialTheme.typography.bodyLarge)

            }
        }
    )
}

fun generateQRCodeBitmap(qrData : String, context: android.content.Context): ImageBitmap {
    val writer = QRCodeWriter()
    val bitMatrix = writer.encode(qrData, BarcodeFormat.QR_CODE, 350, 350)
    val width = bitMatrix.width
    val height = bitMatrix.height
    val bmp = android.graphics.Bitmap.createBitmap(width, height, android.graphics.Bitmap.Config.RGB_565)
    for (x in 0 until width) {
        for (y in 0 until height) {
            bmp.setPixel(x, y, if (bitMatrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE)
        }
    }
    return bmp.asImageBitmap()
}


@Preview
@Composable
fun PreviewParkingTicketScreen() {
    ParkingTicketScreen(
        startTime = "10:00 PM",
        endTime = "12:00 PM",
        spotNumber = "B20",
        duration = "2 hours",
        price = 100.0,
        lotName = "Mic Mac Mall Parking",
        onBackClicked = {}
    )
}
