package com.parkspace.finder.data

data class BookingDetails(
    var id: String? = null,
    val userEmail: String = "",
    val startTime: String = "",
    val endTime: String = "",
    val spotNumber: String = "",
    val price: Double = 0.0,
    val lotId: String = "",
    val bookingDate: String = "",
    val vehicleType: String = "",
    var status: String = "Confirmed"
)

