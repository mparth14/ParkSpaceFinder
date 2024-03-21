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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.parkspace.finder.navigation.ROUTE_PARKING_TICKET
import com.parkspace.finder.navigation.ROUTE_PAYMENT_SUCCESS

@Composable
fun PaymentSuccessScreen(navController : NavController, onViewTicketClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .clip(MaterialTheme.shapes.medium)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
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
                text = "Success!",
                style = MaterialTheme.typography.displayLarge.copy(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Thank you",
                style = MaterialTheme.typography.labelMedium.copy(
                    color = Color.Black,
                    fontSize = 24.sp
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
                onClick = { navController.navigate(ROUTE_PARKING_TICKET)}
            ) {
                Text(text = "View parking ticket")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSuccessScreen() {
    val navController = rememberNavController()
    PaymentSuccessScreen(navController = navController, onViewTicketClick = {})
}
