package com.parkspace.finder.data

interface ParkingSpaceRepository {
    suspend fun getParkingSpaces(): Resource<List<ParkingSpace>>
    suspend fun getParkingSpacesSortedByPriceHighToLow(): Resource<List<ParkingSpace>>
    suspend fun getParkingSpacesSortedByPriceLowToHigh(): Resource<List<ParkingSpace>>
}