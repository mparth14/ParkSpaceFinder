package com.parkspace.finder.ui.payment

data class BookingDetails(
    val startTime: String,
    val endTime: String,
    val spotNumber: String,
    val price: Double,
    val lotId: String,
    val bookingDate: String,
)

