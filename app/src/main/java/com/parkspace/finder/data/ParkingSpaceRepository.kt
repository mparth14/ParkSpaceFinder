package com.parkspace.finder.data

/**
 * Interface defining operations for managing parking spaces.
 */
interface ParkingSpaceRepository {
    /**
     * Retrieves all parking spaces.
     * @return A resource representing the result of the operation.
     */
    suspend fun getParkingSpaces(): Resource<List<ParkingSpace>>

    /**
     * Retrieves parking spaces sorted by price from high to low.
     * @return A resource representing the result of the operation.
     */
    suspend fun getParkingSpacesSortedByPriceHighToLow(): Resource<List<ParkingSpace>>

    /**
     * Retrieves parking spaces sorted by price from low to high.
     * @return A resource representing the result of the operation.
     */
    suspend fun getParkingSpacesSortedByPriceLowToHigh(): Resource<List<ParkingSpace>>

    /**
     * Retrieves parking spaces sorted by rating.
     * @param descending Whether to sort in descending order.
     * @return A resource representing the result of the operation.
     */
    suspend fun getParkingSpacesSortedByRating(descending: Boolean): Resource<List<ParkingSpace>>

    /**
     * Searches for parking spaces based on a query.
     * @param query The search query.
     * @return A resource representing the result of the operation.
     */
    suspend fun searchParkingSpaces(query: String): Resource<List<ParkingSpace>>

    /**
     * Retrieves a parking space by its name.
     * @param name The name of the parking space.
     * @return The parking space if found, or null otherwise.
     */
    suspend fun getParkingSpaceByName(name: String): ParkingSpace?

    /**
     * Retrieves a parking space by its ID.
     * @param id The ID of the parking space.
     * @return A resource representing the result of the operation.
     */
    suspend fun getParkingSpaceById(id: String): Resource<ParkingSpace?>
}
