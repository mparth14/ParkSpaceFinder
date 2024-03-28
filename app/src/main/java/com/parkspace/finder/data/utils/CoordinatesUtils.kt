package com.parkspace.finder.data.utils

import com.google.android.gms.maps.model.LatLng
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