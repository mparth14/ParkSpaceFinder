package com.parkspace.finder.ui.payment

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.parkspace.finder.navigation.ROUTE_PAYMENT_SUCCESS
import com.google.firebase.firestore.FirebaseFirestore
import com.parkspace.finder.data.BookingViewModel
import com.parkspace.finder.data.ParkingSpace
import com.parkspace.finder.data.Resource
import com.parkspace.finder.data.utils.formatTime
import kotlin.random.Random


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(navController: NavHostController, parkingId: String, backStackEntry: NavBackStackEntry) {
    val myBackStackEntry = remember(backStackEntry) {
        navController.getBackStackEntry("parking/${parkingId}/book")
    }
    val bookingViewModel: BookingViewModel = hiltViewModel(myBackStackEntry)
    val startSelectionTime = bookingViewModel.startTimeSelection.collectAsState()
    val endSelectionTime = bookingViewModel.endTimeSelection.collectAsState()
    val dateSelection = bookingViewModel.dateSelection.collectAsState()
    val parkingSpace = bookingViewModel.parkingSpace.collectAsState()
    when (parkingSpace.value) {
        is Resource.Success -> {
            val space = (parkingSpace.value as Resource.Success<ParkingSpace?>).result
            val bookingDetails = BookingDetails(
                startTime = formatTime(startSelectionTime.value),
                endTime = formatTime(endSelectionTime.value),
                spotNumber = Random.Default.nextInt(1, 10000).toString(),
                price = space?.hourlyPrice ?: 0.0,
                lotId = space?.id ?: "",
                bookingDate = dateSelection.value.toString()
            )

            val db = FirebaseFirestore.getInstance()

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
                                text = "Payment",
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 30.dp)
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = { navController.popBackStack()}) {
                                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                            }
                        }
                    )
                }
            ) {
                var cardNumber by remember { mutableStateOf("") }
                var cardHolderName by remember { mutableStateOf("") }
                var expiryDate by remember { mutableStateOf("") }
                var cvv by remember { mutableStateOf("") }
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    PaymentTextField(
                        label = "Card Number",
                        placeholder = "Enter card number",
                        text = cardNumber,
                        onTextChanged = { newCardNumber ->
                            if (newCardNumber.length <= 16) {
                                cardNumber = newCardNumber
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardType = KeyboardType.Number
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    PaymentTextField(
                        label = "Card Holder Name",
                        placeholder = "Enter card holder name",
                        text = cardHolderName,
                        onTextChanged = { cardHolderName = it },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardType = KeyboardType.Text
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        PaymentTextField(
                            label = "Expiry Date",
                            placeholder = "MM/YY",
                            text = expiryDate,
                            onTextChanged = { newExpiryDate ->
                                if (newExpiryDate.length <= 5) {
                                    expiryDate = newExpiryDate
                                }
                            },
                            modifier = Modifier.weight(1f),
                            keyboardType = KeyboardType.Number
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        PaymentTextField(
                            label = "CVV",
                            placeholder = "CVV",
                            text = cvv,
                            onTextChanged = { newCvv ->
                                if (newCvv.length <= 3) {
                                    cvv = newCvv
                                }
                            },
                            modifier = Modifier.weight(1f),
                            keyboardType = KeyboardType.Number
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            db.collection("bookings")
                                .add(bookingDetails)
                                .addOnSuccessListener { documentReference ->
                                    // Navigate to the success screen
                                   // navController.navigate("$ROUTE_PAYMENT_SUCCESS/${bookingDetails.startTime}/${bookingDetails.endTime}/${bookingDetails.spotNumber}/${bookingDetails.duration}/${bookingDetails.price}/${bookingDetails.lotName}")
                                }
                                .addOnFailureListener { e ->
                                }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Pay Now")
                    }
                }
            }
        }
        is Resource.Loading -> {
            Text(text = "Loading...")
        }

        else -> {
            Text(text = "Error")}
    }
}

@Composable
fun PaymentTextField(
    label: String,
    placeholder: String,
    text: String,
    onTextChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType
) {
    TextField(
        value = text,
        onValueChange = { newText -> onTextChanged(newText) },
        label = { Text(text = label) },
        placeholder = { Text(text = placeholder) },
        modifier = modifier,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType)
    )
}
