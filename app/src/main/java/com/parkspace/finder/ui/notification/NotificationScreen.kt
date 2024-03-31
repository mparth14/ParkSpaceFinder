/*
 * This file contains the NotificationScreen composable function and its related NotificationCard composable function.
 */
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlin.random.Random

/*
 * Composable function to display a list of notifications.
 */
@Composable
fun NotificationScreen(notifications: List<String>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = "Notifications",
            style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        // Display existing notifications
        notifications.forEach { notification ->
            NotificationCard(
                title = notification,
                subtitle = "Subtitle placeholder",
                timestamp = "Timestamp placeholder"
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        repeat(Random.nextInt(4, 6)) {
            val notificationType = Random.nextInt(1, 4)
            val title = when (notificationType) {
                1 -> "Your booking is confirmed"
                2 -> "Payment was successful"
                else -> "Payment failed"
            }
            val subtitle = when (notificationType) {
                1 -> "Your parking slot is reserved."
                2 -> "Amount of $${Random.nextInt(10, 100)} has been deducted."
                else -> "Please check your payment details and try again."
            }
            val timestamp = "${Random.nextInt(1, 24)} hours ago"
            NotificationCard(
                title = title,
                subtitle = subtitle,
                timestamp = timestamp
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
//fun NotificationScreen(notifications: List<String>){
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White)
//            .padding(16.dp)
//    ) {
//        Text(
//            text = "Notifications",
//            style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold),
//            modifier = Modifier.padding(bottom = 16.dp)
//        )
//        // Random notifications
//        repeat(Random.nextInt(4, 6)) {
//            val notificationType = Random.nextInt(1, 4)
//            val title = when (notificationType) {
//                1 -> "Your booking is confirmed"
//                2 -> "Payment was successful"
//                else -> "Payment failed"
//            }
//            val subtitle = when (notificationType) {
//                1 -> "Your parking slot is reserved."
//                2 -> "Amount of $${Random.nextInt(10, 100)} has been deducted."
//                else -> "Please check your payment details and try again."
//            }
//            val timestamp = "${Random.nextInt(1, 24)} hours ago"
//            NotificationCard(
//                title = title,
//                subtitle = subtitle,
//                timestamp = timestamp
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//        }
//    }
//}

/*
 * Composable function to display a notification card.
 *
 * @param title: The title of the notification.
 * @param subtitle: The subtitle of the notification.
 * @param timestamp: The timestamp of the notification.
 */
@Composable
fun PaymentSuccessNotification(notifications: List<String>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = "Payment Success",
            style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        // Display existing notifications
        notifications.forEach { notification ->
            NotificationCard(
                title = notification,
                subtitle = "Subtitle placeholder",
                timestamp = "Timestamp placeholder"
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
@Composable
fun NotificationCard(title: String, subtitle: String, timestamp: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Title
                Text(
                    text = title,
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            // Subtitle
            Text(
                text = subtitle,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(start = 16.dp) // Indent the subtitle
            )
            Spacer(modifier = Modifier.height(8.dp))
            // Timestamp
            Text(
                text = timestamp,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp) // Indent the timestamp
            )
        }
    }
}