/**
 * Repository interface for handling booking detail related operations.
 */
package com.parkspace.finder.data

interface BookingDetailRepository {
    /**
     * Makes a booking with the provided booking details.
     * @param bookingDetails The details of the booking.
     * @return A resource containing the result of the booking operation.
     */
    suspend fun makeBooking(bookingDetails: BookingDetails): Resource<String>

    /**
     * Retrieves the booking detail associated with the given booking ID.
     * @param bookingId The ID of the booking.
     * @return A resource containing the result of the operation.
     */
    suspend fun getBookingDetail(bookingId: String): Resource<BookingDetails>

    /**
     * Retrieves the list of booking details associated with the given email.
     * @param email The email of the user.
     * @return A resource containing the list of booking details.
     */
    suspend fun getBookingDetailsByEmail(email: String): Resource<List<BookingDetails>>
}
