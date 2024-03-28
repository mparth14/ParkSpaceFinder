package com.parkspace.finder.data

interface ParkingSpaceRepository {
    suspend fun getParkingSpaces(): Resource<List<ParkingSpace>>
    suspend fun getParkingSpaceByName(name: String): ParkingSpace?

}