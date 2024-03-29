import android.util.Log
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.parkspace.finder.R
import com.parkspace.finder.data.BookingDetails
import com.parkspace.finder.data.ParkingSpace
import com.parkspace.finder.data.Resource
import com.parkspace.finder.navigation.ROUTE_BOOKING_DETAIL
import com.parkspace.finder.ui.bookings.BookingItem
import com.parkspace.finder.viewmodel.AllBookingsDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen(navController: NavController, allBookingsDetailViewModel: AllBookingsDetailViewModel = hiltViewModel()) {
    var selectedTab by remember { mutableIntStateOf(0) }

    val bookings = allBookingsDetailViewModel.bookings.collectAsState();
    val parkingSpaces = allBookingsDetailViewModel.parkingSpaces.collectAsState();
    val tabs = listOf("Active", "Completed")
//    val bookingItems = listOf(
//        BookingItem("1", "Blue Skies Parking", "Monday, October 24", "8:00 AM - 12:00 PM", "$60", "Confirmed"),
//        BookingItem("2", "North Cerulean District", "Saturday, October 22", "8:00 AM - 7:00 PM", "$24", "Completed"),
//        BookingItem("3", "Splitter Garage", "Friday, October 21", "8:00 AM - 4:00 PM", "$45", "Cancelled"),
//        BookingItem("4", "Park It Down", "Wednesday, October 19", "8:00 AM - 12:00 PM", "$34", "Completed"),
//        BookingItem("5", "Jester Park", "Tuesday, October 18", "8:00 AM - 11:00 AM", "$64", "Confirmed"),
//        BookingItem("6", "Jester Park", "Tuesday, October 18", "8:00 AM - 11:00 AM", "$64", "Cancelled"),
//        BookingItem("7", "Jester Park", "Tuesday, October 18", "8:00 AM - 11:00 AM", "$64", "Confirmed"),
//        BookingItem("8", "Jester Park", "Tuesday, October 18", "8:00 AM - 11:00 AM", "$64", "Cancelled"),
//        BookingItem("9", "Jester Park", "Tuesday, October 18", "8:00 AM - 11:00 AM", "$64", "Confirmed"),
//        BookingItem("10", "Jester Park", "Tuesday, October 18", "8:00 AM - 11:00 AM", "$64", "Cancelled"),
//        BookingItem("11", "Jester Park", "Tuesday, October 18", "8:00 AM - 11:00 AM", "$64", "Confirmed"),
//        BookingItem("12", "Jester Park", "Tuesday, October 18", "8:00 AM - 11:00 AM", "$64", "Cancelled"),
//        BookingItem("13", "Jester Park", "Tuesday, October 18", "8:00 AM - 11:00 AM", "$64", "Confirmed")
//    )
    when(bookings.value){
        is Resource.Success -> {
            val bookingItems = (bookings.value as Resource.Success<List<BookingDetails>>).result
            Log.d("BookingScreen", bookingItems.toString())
            val parkingSpaceMap = if(parkingSpaces.value is Resource.Success) {
                (parkingSpaces.value as Resource.Success<Map<String, ParkingSpace>>).result
            } else {
                emptyMap()
            }
            Scaffold(
                topBar = {
                    TopAppBar(title = {
                        Text(
                            "Bookings", style = MaterialTheme.typography.headlineLarge.copy(
                                fontWeight = FontWeight.ExtraBold
                            )
                        )
                    })
                }
            ) { innerPadding ->
                Column(modifier = Modifier.padding(innerPadding)) {
                    TabRow(selectedTabIndex = selectedTab) {
                        tabs.forEachIndexed { index, title ->
                            Tab(
                                selected = selectedTab == index,
                                onClick = { selectedTab = index },
                                text = {
                                    Text(
                                        title,
                                        color = if (selectedTab == index) Color.Black else Color.Gray,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            )
                        }
                    }
                    val filteredBookings = when (selectedTab) {
                        0 -> bookingItems.filter { it.status == "Confirmed" || it.status == "Cancelled" }
                        else -> bookingItems.filter { it.status == "Completed" }
                    }

                    BookingList(bookingItems = filteredBookings, navController, parkingSpaceMap)
                }
            }
        }
        is Resource.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is Resource.Failure -> {
            Text("Failed to load bookings")
        }
        else -> {
            Text("No bookings found")
        }

    }
}

@Composable
fun BookingList(bookingItems: List<BookingDetails>, navController: NavController, parkingSpaces: Map<String, ParkingSpace>) {
    LazyColumn(contentPadding = PaddingValues(all = 8.dp)) {
        items(bookingItems) { booking ->
            BookingItemCard(booking = booking, parkingSpaces = parkingSpaces) {
                // Determine the navigation action based on the booking status
                when (booking.status) {
                    "Confirmed" -> {
                        // Navigate to the detailed screen for active bookings
                        navController.navigate(ROUTE_BOOKING_DETAIL.replace("{bookingId}", booking.id
                            ?: ""))
                    }
                    "Completed" -> {
                        // Navigate to the detailed screen for completed bookings
                        navController.navigate("completedBookingScreen/${booking.id}")
                    }
                    else -> {
                        // Navigate to the detailed screen for cancelled bookings
                        navController.navigate("cancelledBookingScreen/${booking.id}")
                    }
                }
            }
        }
    }
}

@Composable
fun BookingItemCard(booking: BookingDetails, parkingSpaces: Map<String, ParkingSpace>, onClick: () -> Unit) {
    val parkingSpace = parkingSpaces[booking.lotId]
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RectangleShape,
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberAsyncImagePainter(parkingSpace?.imageURL), // Replace with actual image resource
                    contentDescription = "Booking Image",
                    modifier = Modifier
                        .size(88.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = parkingSpace?.name ?: "",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = booking.price.toString(),
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = booking.bookingDate,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = booking.startTime + " - " + booking.endTime,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(10.dp)
            ) {
                BookingStatusBadge(status = booking.status)
            }
        }
    }
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 0.dp),
        thickness = 1.dp,
        color = Color.LightGray
    )
}

@Composable
fun BookingStatusBadge(status: String) {
    val backgroundColor = when (status) {
        "Confirmed" -> Color(0xFF4CAF50)
        "Cancelled" -> Color(0xFFF44336)
        else -> Color(0xFF616161)
    }
    val textColor = Color(0xFFFFFFFF)

    Badge(
        containerColor  = backgroundColor,
        contentColor = Color.Transparent
    ) {
        Text(
            text = status,
            color = textColor,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(4.dp)
        )
    }
}
