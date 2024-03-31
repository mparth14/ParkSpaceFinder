package com.parkspace.finder.ui.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.parkspace.finder.R
import java.util.concurrent.TimeUnit

// Step 1: Create Notification Channels
const val NOTIFICATION_WORK_NAME = "notification_work"
const val SUCCESS_CHANNEL_ID = "success_channel"
const val TIME_LEFT_CHANNEL_ID = "time_left_channel"
const val NOTIFICATION_TITLE_KEY = "notification_title"
const val NOTIFICATION_MESSAGE_KEY = "notification_message"
const val CHANNEL_ID = "your_channel_id" // Replace "your_channel_id" with your desired channel ID
const val NOTIFICATION_ID = 1 // Notification ID to uniquely identify each notification

object NotificationUtils {

    fun createNotificationChannel(context: Context, channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = "Notification channel for parking reminders"
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}

private fun createNotificationChannels(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val notificationManager = context.getSystemService(NotificationManager::class.java)

        val successChannel = NotificationChannel(
            SUCCESS_CHANNEL_ID,
            "Success Notifications",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        // Configure the channel, e.g., set description, importance, etc.
        // notificationManager.createNotificationChannel(successChannel)

        val timeLeftChannel = NotificationChannel(
            TIME_LEFT_CHANNEL_ID,
            "Time Left Notifications",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        // Configure the channel, e.g., set description, importance, etc.
        // notificationManager.createNotificationChannel(timeLeftChannel)
    }
}

fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "Your Channel Name"
        val descriptionText = "Your channel description"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}

// Step 2: Schedule Notifications with WorkManager
fun scheduleNotification(
    context: Context,
    delay: Int,
    notificationId: Int,
    channelId: String,
    title: String,
    message: String
) {
    val workManager = WorkManager.getInstance(context)
    val notificationData = Data.Builder()
        .putString(NOTIFICATION_TITLE_KEY, title)
        .putString(NOTIFICATION_MESSAGE_KEY, message)
        .build()

    val notificationRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
        .setInitialDelay(delay.toLong(), TimeUnit.MILLISECONDS)
        .setInputData(notificationData)
        .build()

    workManager.enqueueUniqueWork(
        NOTIFICATION_WORK_NAME,
        ExistingWorkPolicy.REPLACE,
        notificationRequest
    )
}

// Step 3: Define NotificationWorker to show notifications
class NotificationWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        val title = inputData.getString(NOTIFICATION_TITLE_KEY)
        val message = inputData.getString(NOTIFICATION_MESSAGE_KEY)

        title?.let {
            showNotification(applicationContext, it, message ?: "")
        }
        return Result.success()
    }
}

// Step 4: Show Notifications using NotificationManager
fun showNotification(context: Context, title: String, message: String) {
    val notificationManager = ContextCompat.getSystemService(
        context,
        NotificationManager::class.java
    ) as NotificationManager

    val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setContentTitle(title)
        .setContentText(message)
        .setSmallIcon(R.drawable.ic_notification)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setAutoCancel(true)

    // Show the notification
    notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
}
