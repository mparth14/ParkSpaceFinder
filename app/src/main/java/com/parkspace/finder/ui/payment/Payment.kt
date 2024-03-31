package com.parkspace.finder.ui.payment

/*
 * This file contains composable functions related to the payment UI.
 */
import android.annotation.SuppressLint
import android.util.Log
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.parkspace.finder.data.BookingViewModel
import com.parkspace.finder.data.Resource

/**
 * Composable function for the payment screen.
 * @param navController NavHostController to handle navigation
 * @param parkingId ID of the parking space
 * @param backStackEntry NavBackStackEntry to track back stack
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(navController: NavHostController, parkingId: String, backStackEntry: NavBackStackEntry) {
    val myBackStackEntry = remember(backStackEntry) {
        navController.getBackStackEntry("parking/${parkingId}/book")
    }
    val bookingViewModel: BookingViewModel = hiltViewModel(myBackStackEntry)
    val parkingSpace = bookingViewModel.parkingSpace.collectAsState()
    when (parkingSpace.value) {
        is Resource.Success -> {
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
                    val bookingStatus = bookingViewModel.bookingStatus.collectAsState()
                    Button(
                        onClick = {bookingViewModel.saveBooking()},
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        when (bookingStatus.value) {
                            is Resource.Success -> {
                                val bookingId = (bookingStatus.value as Resource.Success<String>).result
                                Log.d("PaymentScreen", "Booking id: $bookingId")
                                navController.navigate("booking/$bookingId/success")
                            }
                            is Resource.Loading -> {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(24.dp),
                                    color = Color.White
                                )
                            }
                            else -> {
                                Text(text = "Book Now")
                            }
                        }
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

/**
 * Composable function for a payment text field.
 * @param label Label for the text field
 * @param placeholder Placeholder text for the text field
 * @param text Current value of the text field
 * @param onTextChanged Lambda function to handle text changes
 * @param modifier Modifier for the text field
 * @param keyboardType Keyboard type for the text field
 */
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
