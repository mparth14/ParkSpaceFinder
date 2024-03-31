package com.parkspace.finder.ui.booking

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import com.parkspace.finder.R

/**
 * Composable function for the header of the booking screen.
 *
 * @param navController: NavHostController for navigation.
 */
@Composable
fun BookingHeader(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                painter = painterResource(id = R.drawable.back_24),
                contentDescription = "back icon"
            )
        }
        Text(
            text = "Enter Details",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.align(Alignment.Center),
            fontWeight = FontWeight.Bold
        )
    }
}