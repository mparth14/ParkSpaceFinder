/**
 * Utility functions for handling geographical calculations and operations.
 */
package com.parkspace.finder.data.utils

import android.content.Context
import android.location.Geocoder
import android.util.Log
import android.widget.Toast
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.GeoPoint
import java.util.Locale
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * Converts degrees to radians.
 */
fun Double.toRadians() = this * (Math.PI / 180)

/**
 * Calculates the distance between two geographical points using the Haversine formula.
 * @param start The starting point (latitude, longitude).
 * @param end The ending point (latitude, longitude).
 * @return The distance between the two points in kilometers.
 */
fun distanceBetween(start: LatLng, end: LatLng): Double {
    val earthRadius = 6371 // Approx Earth radius in KM

    val startLat = start.latitude.toRadians()
    val startLon = start.longitude.toRadians()
    val endLat = end.latitude.toRadians()
    val endLon = end.longitude.toRadians()

    val dLat = endLat - startLat
    val dLon = endLon - startLon

    val a = sin(dLat / 2).pow(2) + cos(startLat) * cos(endLat) * sin(dLon / 2).pow(2)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))

    return earthRadius * c
}

/**
 * Retrieves the address from a given latitude and longitude using the device's Geocoder.
 * @param context The context of the application.
 * @param latLng The latitude and longitude as a GeoPoint object.
 * @return The address corresponding to the given latitude and longitude.
 */
fun getAddressesFromLatLng(context: Context, latLng: GeoPoint ): String {
    val geocoder = Geocoder(context, Locale.getDefault())
    return try {
        val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
        val address = addresses?.get(0)
        "${address?.getAddressLine(0)}, ${address?.locality}, ${address?.adminArea}, ${address?.countryName}"
    } catch (e: Exception) {
        Log.d("Error", e.toString())
        e.toString()
    }
}
