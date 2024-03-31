package com.parkspace.finder.ui.timerParkingBooking

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.parkspace.finder.R
import com.parkspace.finder.data.Resource
import com.parkspace.finder.ui.notification.NotificationUtils
import com.parkspace.finder.ui.notification.scheduleNotification
import com.parkspace.finder.viewmodel.BookingDetailViewModel
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ParkingTimerScreen(
    navController: NavHostController,
    bookingId: String,
) {
    val bookingDetailViewModel =
        hiltViewModel<BookingDetailViewModel, BookingDetailViewModel.Factory> {
            it.create(bookingId)
        }

    val bookingDetails = bookingDetailViewModel.bookingDetail.collectAsState()
    val parkingSpace = bookingDetailViewModel.bookedParkingSpace.collectAsState()
    when (bookingDetails.value) {
        is Resource.Success -> {
            val bookingDetail = (bookingDetails.value as Resource.Success).result
            val startTimeMillis = convertTimeStringToMillis(bookingDetail.startTime)
            val endTimeMillis = convertTimeStringToMillis(bookingDetail.endTime)
            val totalTimeMillis = if (endTimeMillis > startTimeMillis) {
                endTimeMillis - startTimeMillis
            } else {
                // This case handles when the end time is on the next day.
                val oneDayMillis = TimeUnit.DAYS.toMillis(1)
                (endTimeMillis + oneDayMillis) - startTimeMillis
            }

            var remainingTimeMillis by remember { mutableStateOf(totalTimeMillis) }
            val totalTime =
                String.format("%02d hours", TimeUnit.MILLISECONDS.toHours(totalTimeMillis))

            LaunchedEffect(key1 = Unit) { // Using Unit for simplicity; consider using proper keys for production
                while (true) {
                    val nowMillis = System.currentTimeMillis()
                    remainingTimeMillis = maxOf(0, endTimeMillis - nowMillis)

                    if (remainingTimeMillis <= 0) {
                        break
                    }

                    delay(1000) // Refresh every second
                }
            }


            val remainingTime = calculateRemainingTime(remainingTimeMillis)
            if (remainingTimeMillis == totalTimeMillis) {
                SetBeginTimerNotification(LocalContext.current)
            }

            // Call SetReminder function when 10 minutes are left
            if (remainingTimeMillis <= 10 * 60 * 1000) { // 10 minutes in milliseconds
                SetReminder(LocalContext.current)
            }

            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(text = "Parking Timer") },
                        navigationIcon = {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                            }
                        }
                    )
                },
                content = {
                    // Parking details content
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color.LightGray, shape = RoundedCornerShape(8.dp))
                            .padding(6.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(400.dp)
                                .padding(0.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            // Draw countdown ring
                            CountdownRing(
                                remainingTimeMillis = remainingTimeMillis,
                                totalTimeMillis = totalTimeMillis
                            )
                            // Image in the center
                            Image(
                                painter = painterResource(id = R.drawable.car_timer), // Replace with your image resource
                                contentDescription = "Center Image",
                                modifier = Modifier.size(100.dp) // Adjust size as needed
                            )
                        }
                        // Display remaining time
                        Text(
                            text = remainingTime,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(top = 10.dp), // Add padding if needed
                            style = MaterialTheme.typography.headlineLarge
                        )

                        // Display remaining time label
                        Text(
                            text = "Remaining parking time",
                            modifier = Modifier.padding(bottom = 20.dp), // Add padding if needed
                            style = MaterialTheme.typography.bodyLarge
                        )

                        // second box
                        Box(
                            modifier = Modifier
                                .size(width = 360.dp, height = 200.dp)
                                .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                                .padding(10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column {
                                Row {
                                    Text(
                                        text = "Spot ${bookingDetail.spotNumber}",
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(end = 16.dp), // Add spacing between texts
                                        style = MaterialTheme.typography.titleLarge
                                    )
                                }
                                Spacer(modifier = Modifier.height(30.dp))
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    // First column
                                    Column {
                                        Row {
                                            Text(
                                                text = "Start Time:",
                                                modifier = Modifier.padding(end = 16.dp), // Add spacing between texts
                                                style = MaterialTheme.typography.bodyLarge
                                            )
                                        }
                                        Row {
                                            Text(
                                                text = bookingDetail.startTime,
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier.padding(end = 16.dp), // Add spacing between texts
                                                style = MaterialTheme.typography.bodyLarge
                                            )
                                        }
                                    }
                                    // Second column
                                    Column {
                                        Row {
                                            Box(
                                                modifier = Modifier
                                                    .padding(2.dp) // Add padding around the text
                                                    .border(
                                                        2.dp,
                                                        MaterialTheme.colorScheme.primary,
                                                        RoundedCornerShape(16.dp)
                                                    ) // Add border with purple color and rounded corners
                                            ) {
                                                Text(
                                                    text = totalTime,
                                                    modifier = Modifier.padding(6.dp), // Add padding inside the border
                                                    style = MaterialTheme.typography.bodyLarge
                                                )
                                            }
                                        }
                                    }
                                    // Third column
                                    Column {
                                        Row {
                                            Text(
                                                text = "End Time:",
                                                modifier = Modifier.padding(end = 16.dp), // Add spacing between texts
                                                style = MaterialTheme.typography.bodyLarge
                                            )
                                        }
                                        Row {
                                            Text(
                                                text = bookingDetail.endTime,
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier.padding(end = 16.dp), // Add spacing between texts
                                                style = MaterialTheme.typography.bodyLarge
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .size(width = 360.dp, height = 200.dp)
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Button(
                                onClick = { },
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.size(width = 150.dp, height = 50.dp)
                            ) {
                                Text("Extend Time")
                            }
                            Spacer(modifier = Modifier.width(7.dp)) // Add space between buttons
                            Button(
                                onClick = { },
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.size(width = 150.dp, height = 50.dp)
                            ) {
                                Text("End Parking")
                            }
                        }

                    }
                }
            )
        }

        is Resource.Loading -> {
            Text(text = "Loading...")
        }

        is Resource.Failure -> {
            Text(text = "Error")
        }

        else -> {
            Text(text = "Error")
        }
    }
}
@Composable
fun SetBeginTimerNotification(context: Context) {
    val title = "Parking Timer Started"
    val message = "Your parking timer has begun."

    val CHANNEL_ID = "your_channel_id"

    // Create the notification channel
    NotificationUtils.createNotificationChannel(context, CHANNEL_ID, "Parking Timer")

    // Schedule the notification
    scheduleNotification(
        context = context,
        delay = 0, // Notification should be sent immediately
        notificationId = 2, // Use a different notification ID to differentiate from other notifications
        channelId = CHANNEL_ID,
        title = title,
        message = message
    )
}

@Composable
fun SetReminder(context: Context) {
    // Get the delay before the reminder from your UI or any other source
    val delayBeforeReminderMillis = 20 * 1000 // 10 minutes before the end of the parking session

    // Title and message for the notification
    val title = "Parking Reminder"
    val message = "Your parking session is ending in 10 minutes."
    val CHANNEL_ID = "your_channel_id"


    // Call the function to create the notification channel
    NotificationUtils.createNotificationChannel(context, CHANNEL_ID, "Parking Reminders")

    // Call the scheduleNotification function to trigger the notification
    scheduleNotification(
        context = context,
        delay = delayBeforeReminderMillis,
        notificationId = 1,
        channelId = CHANNEL_ID,
        title = title,
        message = message
    )
}

@Composable
fun CountdownRing(remainingTimeMillis: Long, totalTimeMillis: Long) {
    val progress = (totalTimeMillis - remainingTimeMillis).toFloat() / totalTimeMillis.toFloat()
    Log.d("CountdownRing", "Progress: $progress, Remaining: $remainingTimeMillis, Total: $totalTimeMillis")
    val strokeWidth = 15.dp
    val remainingColor = MaterialTheme.colorScheme.primary // Color for the remaining time
    val elapsedColor = Color.Gray // Color for the elapsed time

    Canvas(modifier = Modifier.size(200.dp)) {
        val startAngle = -90f
        val fullCircle = 360f

        // Draw the elapsed time arc (in grey)
        drawArc(
            color = remainingColor,
            startAngle = startAngle,
            sweepAngle = fullCircle,
            useCenter = false,
            style = Stroke(width = strokeWidth.toPx())
        )

        // Draw the remaining time arc (in green)
        drawArc(
            color = elapsedColor,
            startAngle = startAngle,
            sweepAngle = -fullCircle * progress,
            useCenter = false,
            style = Stroke(width = strokeWidth.toPx())
        )
    }
}

fun calculateRemainingTime(remainingMillis: Long): String {
    if (remainingMillis < 0) {
        return "00:00:00" // or handle it as you see fit
    }
    val hours = TimeUnit.MILLISECONDS.toHours(remainingMillis)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(remainingMillis) % TimeUnit.HOURS.toMinutes(1)
    val seconds = TimeUnit.MILLISECONDS.toSeconds(remainingMillis) % TimeUnit.MINUTES.toSeconds(1)
    return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds)
}

fun convertTimeStringToMillis(timeString: String): Long {
    val timeFormatter = SimpleDateFormat("hh:mm a", Locale.getDefault())
    val currentDate = Calendar.getInstance()
    val parsedDate = timeFormatter.parse(timeString) ?: return 0L

    val parsedCalendar = Calendar.getInstance().apply {
        time = parsedDate
        set(Calendar.YEAR, currentDate.get(Calendar.YEAR))
        set(Calendar.MONTH, currentDate.get(Calendar.MONTH))
        set(Calendar.DAY_OF_MONTH, currentDate.get(Calendar.DAY_OF_MONTH))
    }

    // This will handle the scenario where the time has already passed for today,
    // and we should be looking at the same time for tomorrow.
    if (parsedCalendar.before(currentDate)) {
        parsedCalendar.add(Calendar.DAY_OF_MONTH, 1)
    }

    return parsedCalendar.timeInMillis
}