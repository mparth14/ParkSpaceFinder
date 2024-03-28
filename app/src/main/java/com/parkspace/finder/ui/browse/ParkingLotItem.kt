package com.parkspace.finder.ui.browse

import android.media.Rating
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.parkspace.finder.R
import com.parkspace.finder.data.ParkingSpace
import com.parkspace.finder.navigation.ROUTE_BROWSE
import com.parkspace.finder.navigation.ROUTE_PARKING_DETAIL

@Composable
fun Rating(rating: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1..5) {
            Icon(
                painter = painterResource(id = if (i <= rating) R.drawable.star_filled_24 else R.drawable.star_empty_24),
                contentDescription = "Rating Star",
                tint = if(i <= rating) Color(0xFFFAAF00) else Color(0xFFAAAAAA),
                modifier = Modifier.size(18.dp)
            )
        }
    }
}


@Composable
fun ParkingLotItem(navController: NavController, parkingSpace: ParkingSpace, onItemClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = {
            navController.navigate("parking_details/${parkingSpace.name}")
        }
    )
    {
        Row(
            modifier = Modifier.padding(8.dp)
        ) {
            // This would be your image
            Image(
                painter = rememberAsyncImagePainter(parkingSpace.imageURL),
                contentDescription = "Crosswalk Lot",
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.CenterVertically),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier
                    .align(Alignment.Top)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(parkingSpace.name, fontWeight = FontWeight.Bold)
                    Row {
                        Icon(
                            painter = painterResource(id = R.drawable.location_24), // Your location icon resource
                            contentDescription = "Location",
                            tint = Color(0xFF777777),
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            "${String.format("%.2f", parkingSpace.distanceFromCurrentLocation)} km away",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF777777)
                        )
                    }
                }
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 8.dp)) {
                    Rating(4)
                    Text(
                        "23 • \$${parkingSpace.hourlyPrice}/hr",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}