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

fun Double.toRadians() = this * (Math.PI / 180)
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