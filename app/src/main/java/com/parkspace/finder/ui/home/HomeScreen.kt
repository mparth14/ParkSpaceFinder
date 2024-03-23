package com.parkspace.finder.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.parkspace.finder.R
import com.parkspace.finder.data.AuthViewModel
import com.parkspace.finder.ui.theme.spacing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.sp



@Composable
fun ParkingSpotRow(
    imageResource: Int,
    title: String,
    distance: String,
    rating: Float,
    price: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        // Image
        Box(
            modifier = Modifier
                .height(105.dp)
                .width(130.dp)
                .padding(end = 16.dp)
                .background(Color.Transparent, RoundedCornerShape(8.dp))
        ) {
            Image(
                painter = painterResource(id = imageResource),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.clip(shape = RoundedCornerShape(16.dp))
            )
        }

        // Column for text fields
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 19.sp
                ),
                modifier = Modifier.padding(start = 3.dp)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.padding(horizontal = 1.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_location_grey),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp) // Adjust size as needed
                )
                Text(
                    text = distance,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color(0xffC8C7CC)
                    ),
                )
            }

            Spacer(modifier = Modifier.height(5.dp))

            // Stars for rating
            Row(
                modifier = Modifier
                    .padding(horizontal = 3.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(5) { index ->
                    Image(
                        painter = painterResource(id = R.drawable.ic_star),
                        contentDescription = null,
                        //tint = if (index < rating.toInt()) Color.Yellow else Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Text(
                    text = price,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.Black,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    ),
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier.padding(end = 5.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_favourite_filled),
                contentDescription = null,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Composable
fun ProductCard(
    price: String,
    productName: String,
    productDescription: String,
    productLocation: String,
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(horizontal = 5.dp)
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier.background(Color.White)
        ) {
            // Image with heart icon and price
            Box(
                modifier = Modifier
                    .height(115.dp)
                    .width(210.dp)
                    .background(Color.White)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = null,
                    contentScale = ContentScale.Crop, // Adjust content scale as needed
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(16.dp))
                )

//                // Heart icon at top right corner
//                Box(
//                    modifier = Modifier
//
//                        .align(Alignment.TopEnd)
//                ) {
//                    Image(
//                        painter = painterResource(id = R.drawable.ic_location),
//                        contentDescription = null,
//                        modifier = Modifier.size(24.dp)
//                    )
//                }

                // Price at bottom right corner
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(9.dp)
                        .background(Color.Black.copy(alpha = 0.65f), RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "$price/hr",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Column(
                modifier = Modifier
                    .padding(horizontal = 5.dp, vertical = 4.dp)
            ){
                Text(
                    text = productName,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontSize = 21.sp
                    ),
                    maxLines = 1,
                )
            }

            Row(
                modifier = Modifier.padding( horizontal = 2.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Image(
                    painter = painterResource(id = R.drawable.ic_location_grey),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp) // Adjust size as needed
                )
                Text(
                    text = productDescription,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color(0xffC8C7CC)
                    ),
                )
            }
            // Product details

            Spacer(modifier = Modifier.height(1.dp))

            // Stars for rating
            Row(
                modifier = Modifier
                    .padding(horizontal = 3.dp, vertical = 4.dp ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(5) { index ->
                    Image(
                        painter = painterResource(id = R.drawable.ic_star),
                        contentDescription = null,
                        //tint = if (index < rating.toInt()) Color.Yellow else Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun TopBar(location: String, cityName: String, onLocationClick: () -> Unit, onSearchClick: () -> Unit) {
    val spacing = MaterialTheme.spacing

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Location icon
        Image(
            painter = painterResource(id = R.drawable.ic_location),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .padding(end = spacing.medium)
                .clickable(onClick = onLocationClick), // Handle click event
        )

        Column(
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Your Location",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFFD4D4D4),
            )

            // City name
            Text(
                text = cityName,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black // Bold and dark black color for city name
                )
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Search icon
        Image(
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
                .clickable(onClick = onSearchClick), // Handle click event
        )
    }
}

@Composable
fun HomeScreen(viewModel: AuthViewModel?,navController: NavHostController) {
    val spacing = MaterialTheme.spacing
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .padding(bottom = 80.dp)
            .verticalScroll(state = scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Code for the Top bar with location and search Icon
        Box(
            modifier = Modifier.padding(spacing.medium)
        ){
            TopBar(
                location = "Your Location", // Replace with location string
                cityName = "San Francisco, California", // Replace with city name
                onLocationClick = { /* Handle location click */ },
                onSearchClick = { /* Handle search click */ }
            )
        }

        Box(
            modifier = Modifier.padding(horizontal = spacing.medium)
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp)
                    .padding(vertical = 9.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .background(color = Color(0xFF1D264F))
                    .padding(13.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    // Column for "Start" time and date
                    Row(modifier = Modifier.weight(1f)) {
                        Column {
                            // Text "Start"
                            Text(
                                text = "Start",
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 7.dp)
                            )

                            // Text "7:00 AM" (Replace with your start time)
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                                    .padding(horizontal = 6.dp)
                                    .background(
                                        color = Color.White,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(8.dp)
                                    .clickable { /* Handle click */ }
                            ) {
                                Text(
                                    text = "7:00 AM", // Replace with your start time
                                    color = Color.Black,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }

                            // Text "3 Feb, 2024" (Replace with your start date)
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 6.dp)
                                    .background(
                                        color = Color.White,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(8.dp)
                                    .clickable { /* Handle click */ }
                            ) {
                                Text(
                                    text = "3 Feb, 2024", // Replace with your start date
                                    color = Color.Black,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }
                    }

                    // Column for "End" time and date
                    Row(modifier = Modifier.weight(1f)) {
                        Column {
                            // Text "End"
                            Text(
                                text = "End",
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 7.dp)
                            )

                            // Text "9:00 PM" (Replace with your end time)
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                                    .padding(horizontal = 4.dp)
                                    .background(
                                        color = Color.White,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(8.dp)
                                    .clickable { /* Handle click */ }
                            ) {
                                Text(
                                    text = "9:00 PM", // Replace with your end time
                                    color = Color.Black,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }

                            // Text "3 Feb, 2024" (Replace with your end date)
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 4.dp)
                                    .background(
                                        color = Color.White,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(8.dp)
                                    .clickable { /* Handle click */ }
                            ) {
                                Text(
                                    text = "3 Feb, 2024", // Replace with your end date
                                    color = Color.Black,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }
                    }
                }

                // Search Button
                Button(
                    onClick = { /* Handle search button click */ },
                    colors = ButtonDefaults.buttonColors(Color(0xFF7C77F6)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 15.dp,
                            bottom = 5.dp
                        ) // Adjust top and bottom padding as needed
                        .height(40.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Search",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Box(
            modifier = Modifier.padding(horizontal = spacing.medium)
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 7.dp, horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "Let's Park Again!",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                )


                Row(
                    modifier = Modifier.clickable { /* Handle click */ },
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = "See all",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.DarkGray
                        )
                    )

                    Image(
                        painter = painterResource(id = R.drawable.ic_arrow_forward),
                        contentDescription = null,
                        modifier = Modifier
                            .size(27.dp)
                            .padding(end = spacing.medium)
                    )


                }
            }
        }


        // Park Again List in Card Form
        Box(
            modifier = Modifier.padding(horizontal = 3.dp) // Set horizontal padding to 0
        ){
            LazyRow {
                items(10) { index ->
                    ProductCard(
                        price = "Price ",
                        productName = "Product ",
                        productDescription = "Description ",
                        productLocation = "Location "
                    )
                }
            }
        }

        //Filter Button
        Button(
            onClick = { /* Handle button click */ },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .background(Color.Transparent, RoundedCornerShape(4.dp)),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xff7C77F6)
            )
        ) {
            Box(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_filter),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Filter",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            }
        }


        Box(
            modifier = Modifier
                .background(Color.White) // Adjust background color as needed

        ) {
            Column{
                repeat(5) { index ->
                    ParkingSpotRow(
                        imageResource = R.drawable.logo,
                        title = "Blue Skies Parking",
                        distance = "0.31 miles 27 available",
                        rating = 4.5f, // Example rating
                        price = "$55/hr",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                    HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
                }
            }
        }

    }
}