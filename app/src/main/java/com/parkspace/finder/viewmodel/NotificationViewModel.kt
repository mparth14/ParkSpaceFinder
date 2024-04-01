package com.parkspace.finder.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class NotificationViewModel : ViewModel() {
    val notifications = mutableStateListOf<Notification>()

    fun addNotification(notification: Notification) {
        notifications.add(notification)
    }
}

data class Notification(val title: String, val subtitle: String, val timestamp: String)
