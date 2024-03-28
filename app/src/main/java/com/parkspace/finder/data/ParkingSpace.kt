package com.parkspace.finder.data

import com.google.firebase.firestore.GeoPoint


data class ParkingSpace(
    val name: String = "",
    val imageURL: String = "",
    val hourlyPrice: Double = 0.0,
    val location: GeoPoint = GeoPoint(0.0, 0.0),
    var distanceFromCurrentLocation: Double = 0.0
) {
}