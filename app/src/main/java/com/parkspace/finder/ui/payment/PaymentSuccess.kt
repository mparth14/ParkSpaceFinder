package com.parkspace.finder.ui.payment

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.parkspace.finder.R
import com.parkspace.finder.data.Resource
import com.parkspace.finder.navigation.ROUTE_PARKING_TICKET
import com.parkspace.finder.viewmodel.BookingDetailViewModel
import kotlinx.coroutines.launch
import nl.dionsegijn.konfetti.compose.KonfettiView

@Composable
fun PaymentSuccessScreen(navController : NavController, bookingId: String) {
    val bookingDetailViewModel = hiltViewModel<BookingDetailViewModel, BookingDetailViewModel.Factory> {
        it.create(bookingId)
    }
    val bookedParkingSpace = bookingDetailViewModel.bookedParkingSpace.collectAsState()
    val context = LocalContext.current
    var confettiAnimationEnabled by remember { mutableStateOf(false) }
    val mediaPlayer = remember { MediaPlayer.create(context, R.raw.successsound) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            mediaPlayer.start()
        }
        confettiAnimationEnabled = true
    }

    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.release()
            confettiAnimationEnabled = false
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .clip(MaterialTheme.shapes.medium)
                .background(Color.White)
                .padding(44.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Success!",
                    style = MaterialTheme.typography.displayLarge.copy(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 54.sp
                    ),
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(Color.Green),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "✓",
                        style = MaterialTheme.typography.displayLarge.copy(
                            color = Color.White,
                            fontSize = 54.sp
                        )
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Thank you",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = Color.Black,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 30.sp
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "For choosing our services and trusting us with your parking needs.",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Color.Black,
                        fontSize = 20.sp
                    )
                )
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    shape = RoundedCornerShape(25),
                    onClick = {
                        navController.navigate(ROUTE_PARKING_TICKET.replace("{bookingId}", bookingId))
                    }
                ) {
                    Text(text = "View parking ticket",
                        fontWeight = FontWeight.Bold)
                }
                when (bookedParkingSpace.value) {
                    is Resource.Success -> {
                        val space = (bookedParkingSpace.value as Resource.Success).result
                        Button(
                            shape = RoundedCornerShape(25),
                            onClick = {
                                val latitude = space?.location?.latitude
                                val longitude = space?.location?.longitude
                                val gmmIntentUri =
                                    Uri.parse("google.navigation:q=$latitude,$longitude")
                                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                                mapIntent.setPackage("com.google.android.apps.maps")
                                context.startActivity(mapIntent)
                            }
                        ) {
                            Text("Let's go to parking!")
                        }
                    }
                    else -> {
                        Button(
                            onClick = {
                            },
                            enabled = false
                        ) {
                            Text("Let's go to parking!")
                        }
                    }
                }
            }
        }
        if (confettiAnimationEnabled) {
            KonfettiView(
                modifier = Modifier.fillMaxSize(),
                parties = KonfettiViewModel()
            )
        }
    }
}
