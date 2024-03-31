package com.parkspace.finder.data

import com.google.firebase.firestore.GeoPoint


/**
 * Data class representing a parking space.
 * @property id The ID of the parking space.
 * @property name The name of the parking space.
 * @property imageURL The URL of the image associated with the parking space.
 * @property hourlyPrice The hourly price of the parking space.
 * @property location The geographic coordinates of the parking space.
 * @property distanceFromCurrentLocation The distance from the current location to the parking space.
 */
data class ParkingSpace(
    var id: String = "",
    val name: String = "",
    val imageURL: String = "",
    val hourlyPrice: Double = 0.0,
    val location: GeoPoint = GeoPoint(0.0, 0.0),
    var distanceFromCurrentLocation: Double = 0.0
)