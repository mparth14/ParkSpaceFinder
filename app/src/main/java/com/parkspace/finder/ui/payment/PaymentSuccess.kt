package com.parkspace.finder.ui.payment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.parkspace.finder.navigation.ROUTE_PARKING_TICKET

@Composable
fun PaymentSuccessScreen(navController : NavController, bookingId: String) {
    Box(
        modifier = Modifier.fillMaxSize()
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
                    onClick = {
                        navController.navigate(
                            "$ROUTE_PARKING_TICKET/123"
                        )
                    }
                ) {
                    Text(text = "View parking ticket",
                        fontWeight = FontWeight.Bold)
                }
                Button(
                    onClick = {
                        //
                    },
                    modifier = Modifier.background(Color.White)
                ) {
                    Text(text = "Let's go to parking!",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }
}
