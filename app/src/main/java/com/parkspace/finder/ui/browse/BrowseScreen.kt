package com.parkspace.finder.ui.browse

import android.Manifest
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.util.Log
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
import androidx.compose.material3.Text
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.parkspace.finder.ui.theme.spacing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.parkspace.finder.R
import com.parkspace.finder.data.ParkingSpace
import com.parkspace.finder.data.ParkingSpaceViewModel
import com.parkspace.finder.data.Resource
import com.parkspace.finder.navigation.ROUTE_REQUEST_LOCATION_PERMISSION
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun ParkingSpotRow(
    imageResource: String,
    title: String,
    distance: String,
    rating: Float,
    price: String,
    modifier: Modifier = Modifier
) {
    val painter: Painter = rememberImagePainter(
        data = imageResource,
        builder = {
            crossfade(true) // Optional - Enables crossfade animation between images
        }
    )
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
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize().clip(shape = RoundedCornerShape(16.dp))
            )
        }

        // Column for text fields
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 16.sp
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
                    text = " $distance",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color(0xff808080)
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
                    text = "· $ $price",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.Black,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    ),
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

//        Box(
//            modifier = Modifier.padding(end = 5.dp)
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.ic_favourite_filled),
//                contentDescription = null,
//                modifier = Modifier.size(30.dp)
//            )
//        }
    }
}

@Composable
fun ProductCard(
    price: String,
    productName: String,
    productDescription: String,
    productLocation: String,
) {
    val painter: Painter = rememberImagePainter(
        data = productDescription,
        builder = {
            crossfade(true) // Optional - Enables crossfade animation between images
        }
    )
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
                    painter = painter,
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
                        text = "$ $price/hr",
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
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontSize = 16.sp
                    ),
                    maxLines = 2,
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
                    text = " 0.25 miles away",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color(0xff808080)
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
                .size(55.dp)
                .padding(end = spacing.medium)
                .clickable(onClick = onLocationClick), // Handle click event
        )

        Column(
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Your Location",
                fontSize = 17.sp,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF808080),
            )

            // City name
            Text(
                text = cityName,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black, // Bold and dark black color for city name
                    fontSize = 16.sp
                )
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Search icon
        Image(
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = null,
            modifier = Modifier
                .size(28.dp)
                .clickable(onClick = onSearchClick), // Handle click event
        )
    }
}



@Composable
@ExperimentalMaterial3Api
fun BrowseScreen(
    context: Context,
    parkingSpaceViewModel: ParkingSpaceViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val permissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    val needsLocationPermission = parkingSpaceViewModel.needsLocationPermission.collectAsState()
    val currentLocation = parkingSpaceViewModel.currentLocation.collectAsState()
    val addresses = parkingSpaceViewModel.addresses.collectAsState()

    val firstAddress = addresses.value.firstOrNull() // Get the first address if available

    var locality: String? = null
    var admin: String? = null
    var countryCode: String? = null
    firstAddress?.let { address ->
        locality = address.locality // Get the locality
        admin = address.adminArea // Get the administrative area (admin)
        countryCode = address.countryCode // Get the country code
    }

    if (needsLocationPermission.value) {
        navController.popBackStack()
        navController.navigate(ROUTE_REQUEST_LOCATION_PERMISSION)
    }

    val parkingSpaces = parkingSpaceViewModel.parkingSpaces
    val state = rememberPullToRefreshState()
    if (state.isRefreshing) {
        LaunchedEffect(true) {
            // fetch something
            delay(1500)
            parkingSpaceViewModel.fetchParkingSpaces()
            state.endRefresh()
        }
    }

    var selectedTimeStart by remember { mutableStateOf("7:00 AM") }
    var selectedTimeEnd by remember { mutableStateOf("9:00 PM") }
    var selectedDateEnd by remember { mutableStateOf(Calendar.getInstance()) }
    var selectedDateStart by remember { mutableStateOf(Calendar.getInstance()) }
    val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
    //var selectedTime by remember { mutableStateOf("") }


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
                cityName = "$locality, $admin, $countryCode", // Replace with city name
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
                                    .clickable {
                                        val timePickerDialog = TimePickerDialog(
                                            context,
                                            { _, hourOfDay, minute ->
                                                // Update selectedTime with the chosen time
                                                val selectedCalendar = Calendar.getInstance().apply {
                                                    set(Calendar.HOUR_OF_DAY, hourOfDay)
                                                    set(Calendar.MINUTE, minute)
                                                }
                                                selectedTimeStart = timeFormat.format(selectedCalendar.time)
                                            },
                                            Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                                            Calendar.getInstance().get(Calendar.MINUTE),
                                            false // 24-hour format
                                        )
                                        timePickerDialog.show()
                                    }
                            ) {
                                Text(
                                    text = selectedTimeStart, // Replace with your start time
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
                                    .clickable {
                                        val datePickerDialog = DatePickerDialog(
                                            context,
                                            { _, year, month, dayOfMonth ->
                                                // Update selectedDate with the chosen date
                                                selectedDateStart = Calendar.getInstance().apply {
                                                    set(Calendar.YEAR, year)
                                                    set(Calendar.MONTH, month)
                                                    set(Calendar.DAY_OF_MONTH, dayOfMonth)
                                                }
                                            },
                                            selectedDateStart.get(Calendar.YEAR),
                                            selectedDateStart.get(Calendar.MONTH),
                                            selectedDateStart.get(Calendar.DAY_OF_MONTH)
                                        )
                                        datePickerDialog.show()
                                    }
                            ) {
                                    Text(
                                        text = buildAnnotatedString {
                                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                                append(dateFormat.format(selectedDateStart.time)) // Format the date
                                            }
                                        }, // Replace with your start date
                                        color = Color.Black,
                                        modifier = Modifier.padding(8.dp),
                                        maxLines = 1
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
                                    .clickable {
                                        val timePickerDialog = TimePickerDialog(
                                            context,
                                            { _, hourOfDay, minute ->
                                                // Update selectedTime with the chosen time
                                                val selectedCalendar = Calendar.getInstance().apply {
                                                    set(Calendar.HOUR_OF_DAY, hourOfDay)
                                                    set(Calendar.MINUTE, minute)
                                                }
                                                selectedTimeEnd = timeFormat.format(selectedCalendar.time)
                                            },
                                            Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                                            Calendar.getInstance().get(Calendar.MINUTE),
                                            false // 24-hour format
                                        )
                                        timePickerDialog.show()
                                    }
                            ) {
                                Text(
                                    text = selectedTimeEnd, // Replace with your start date
                                    color = Color.Black,
                                    modifier = Modifier.padding(8.dp),
                                    maxLines = 1
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
                                    .clickable {
                                        val datePickerDialog = DatePickerDialog(
                                            context,
                                            { _, year, month, dayOfMonth ->
                                                // Update selectedDate with the chosen date
                                                selectedDateEnd = Calendar.getInstance().apply {
                                                    set(Calendar.YEAR, year)
                                                    set(Calendar.MONTH, month)
                                                    set(Calendar.DAY_OF_MONTH, dayOfMonth)
                                                }
                                            },
                                            selectedDateEnd.get(Calendar.YEAR),
                                            selectedDateEnd.get(Calendar.MONTH),
                                            selectedDateEnd.get(Calendar.DAY_OF_MONTH)
                                        )
                                        datePickerDialog.show()
                                    }
                            ) {
                                Text(
                                    text = buildAnnotatedString {
                                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                            append(dateFormat.format(selectedDateEnd.time)) // Format the date
                                        }
                                    }, // Replace with your end date
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
                            top = 13.dp,
                            bottom = 4.dp
                        ) // Adjust top and bottom padding as needed
                        .height(45.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Search",
                        fontSize = 18.sp,
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


//                Row(
//                    modifier = Modifier.clickable { /* Handle click */ },
//                    verticalAlignment = Alignment.CenterVertically
//                ){
//                    Text(
//                        text = "See all",
//                        style = MaterialTheme.typography.bodyMedium.copy(
//                            color = Color.DarkGray
//                        )
//                    )
//
//                    Image(
//                        painter = painterResource(id = R.drawable.ic_arrow_forward),
//                        contentDescription = null,
//                        modifier = Modifier
//                            .size(27.dp)
//                            .padding(end = spacing.medium)
//                    )
//
//
//                }
            }
        }


        // Park Again List in Card Form
        Box(
            modifier = Modifier.padding(horizontal = 3.dp) // Set horizontal padding to 0
        ) {
            val resource by parkingSpaces.collectAsState()
            when (resource) {
                is Resource.Success -> {
                val spaces = (resource as Resource.Success<List<ParkingSpace>>).result
            LazyRow {
                items(spaces) { space ->
                    ProductCard(
                        price = space.hourlyPrice.toString(),
                        productName = space.name.toString(),
                        productDescription = space.imageURL.toString(),
                        productLocation = "Location "
                    )
                    }
                }
            }

                is Resource.Failure -> Text(text = "Error")
                Resource.Loading -> Text(text = "Loading...")
                null -> Text(text = "Error")
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
            val resource by parkingSpaces.collectAsState()
            when (resource) {
                is Resource.Success -> {
                    val spaces = (resource as Resource.Success<List<ParkingSpace>>).result
                    Column {
                        spaces.forEach() { space ->
                            ParkingSpotRow(
                                imageResource = space.imageURL.toString(),
                                price = space.hourlyPrice.toString(),
                                title = space.name.toString(),
                                rating = 4.5f,
                                distance = "0.31 miles · 27 available"
                                //modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }

                is Resource.Failure -> Text(text = "Error")
                Resource.Loading -> Text(text = "Loading...")
                null -> Text(text = "Error")

            }


        }


    }
}

//Column {
//    Surface(
//        modifier = Modifier.zIndex(1f)
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(Color.White)
//                .padding(top = 16.dp, bottom = 4.dp),
//        ) {
//            if (addresses.value.isNotEmpty()) {
//                AddressBar(address = addresses.value[0].getAddressLine(0))
//            }
//            Text(text = "Showing parking lots near you", modifier = Modifier.padding(start = 16.dp, end=16.dp), fontSize = MaterialTheme.typography.headlineSmall.fontSize, fontWeight = FontWeight.Bold)
//        }
//    }
//    Box(
//        Modifier
//            .nestedScroll(state.nestedScrollConnection)
//            .padding(start = 16.dp, end = 16.dp)) {
//        parkingSpaces.value.let {
//            when (it) {
//                is Resource.Success -> {
//                    Column {
//                        LazyColumn(modifier = Modifier.fillMaxSize().padding(bottom = 80.dp)) {
//                            items(it.result!!.size) { index ->
//                                Box(
//                                    modifier = Modifier
//                                        .fillMaxWidth()
//                                        .padding(bottom = 16.dp)
//                                ){
//                                    ParkingLotItem(
//                                        navController = navController,
//                                        parkingSpace = it.result[index],
//                                    )
//                                }
//
//                            }
//                        }
//                    }
//                }
//                is Resource.Failure -> {
//                    Log.d("BrowseScreen", it.exception.message.toString())
//                    val context = LocalContext.current
//                    Toast.makeText(context, it.exception.message, Toast.LENGTH_SHORT).show()
//                }
//
//                is Resource.Loading -> {
//                    Text(text = "Loading...")
//                }
//
//                else -> {
//                    Text(text = "Error")
//                }
//            }
//        }
//        PullToRefreshContainer(
//            modifier = Modifier.align(Alignment.TopCenter),
//            state = state,
//        )
//    }
//}