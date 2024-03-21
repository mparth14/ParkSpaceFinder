package com.parkspace.finder.data

interface ParkingSpaceRepository {
    suspend fun getParkingSpaces(): Resource<List<ParkingSpace>>
}