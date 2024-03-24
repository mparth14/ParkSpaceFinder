import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import com.parkspace.finder.R
import com.parkspace.finder.ui.bookings.BookingItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen() {
    var selectedTab by remember { mutableIntStateOf(0) }
    val bookingItems = listOf(
        BookingItem(1, "Blue Skies Parking", "Monday, October 24", "8:00 AM - 12:00 PM", "$60", "Confirmed"),
        BookingItem(2, "North Cerulean District", "Saturday, October 22", "8:00 AM - 7:00 PM", "$24", "Completed"),
        BookingItem(3, "Splitter Garage", "Friday, October 21", "8:00 AM - 4:00 PM", "$45", "Cancelled"),
        BookingItem(4, "Park It Down", "Wednesday, October 19", "8:00 AM - 12:00 PM", "$34", "Completed"),
        BookingItem(5, "Jester Park", "Tuesday, October 18", "8:00 AM - 11:00 AM", "$64", "Confirmed")
    )
    val tabs = listOf("Active", "Completed")

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Bookings",style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.ExtraBold
            )) })
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            TabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title, color = if (selectedTab == index) Color.Black else Color.Gray, fontWeight = FontWeight.Bold) }
                    )
                }
            }
            val filteredBookings = when (selectedTab) {
                0 -> bookingItems.filter { it.status == "Confirmed" || it.status == "Cancelled" }
                else -> bookingItems.filter { it.status == "Completed" }
            }

            BookingList(bookingItems = filteredBookings)
        }
    }
}

@Composable
fun BookingList(bookingItems: List<BookingItem>) {
    LazyColumn(contentPadding = PaddingValues(all = 8.dp)) {
        items(bookingItems) { booking ->
            BookingItemCard(booking = booking)
        }
    }
}

@Composable
fun BookingItemCard(booking: BookingItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp, vertical = 5.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background), // Replace with actual image resource
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
                            text = booking.title,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = booking.price,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = booking.date,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = booking.time,
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


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MaterialTheme {
        BookingScreen()
    }
}
