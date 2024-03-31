package com.parkspace.finder.data

/**
 * Data class representing the details of a booking.
 * @property id The ID of the booking.
 * @property userEmail The email of the user making the booking.
 * @property startTime The start time of the booking.
 * @property endTime The end time of the booking.
 * @property spotNumber The spot number of the booking.
 * @property price The price of the booking.
 * @property lotId The ID of the parking lot associated with the booking.
 * @property bookingDate The date of the booking.
 * @property vehicleType The type of vehicle for the booking.
 * @property status The status of the booking (default: "Confirmed").
 */
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
