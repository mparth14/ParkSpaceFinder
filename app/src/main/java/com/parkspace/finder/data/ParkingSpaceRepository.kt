package com.parkspace.finder.data

interface ParkingSpaceRepository {
    suspend fun getParkingSpaces(): Resource<List<ParkingSpace>>
    suspend fun getParkingSpacesSortedByPriceHighToLow(): Resource<List<ParkingSpace>>
    suspend fun getParkingSpacesSortedByPriceLowToHigh(): Resource<List<ParkingSpace>>
    suspend fun getParkingSpacesSortedByRating(descending: Boolean): Resource<List<ParkingSpace>>
    suspend fun searchParkingSpaces(query: String): Resource<List<ParkingSpace>>
    suspend fun getParkingSpaceByName(name: String): ParkingSpace?

    suspend fun getParkingSpaceById(id: String): Resource<ParkingSpace?>
}