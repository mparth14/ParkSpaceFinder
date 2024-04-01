/**
 * Implementation of the [BookingDetailRepository] interface for handling booking detail operations using Firebase Firestore.
 */
package com.parkspace.finder.data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.parkspace.finder.data.utils.await
import com.parkspace.finder.data.utils.checkIfDateTimeIsInPast
import javax.inject.Inject

class BookingDetailRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
) : BookingDetailRepository {
    /**
     * Makes a booking with the provided booking details.
     * @param bookingDetails The details of the booking.
     * @return A resource containing the result of the booking operation.
     */
    override suspend fun makeBooking(bookingDetails: BookingDetails): Resource<String> {
        return try {
            val savedData = db.collection("bookings").add(bookingDetails).await()
            Resource.Success(savedData.id)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    /**
     * Retrieves the booking detail associated with the given booking ID.
     * @param bookingId The ID of the booking.
     * @return A resource containing the result of the operation.
     */
    override suspend fun getBookingDetail(bookingId: String): Resource<BookingDetails> {
        return try {
            val result = db.collection("bookings").document(bookingId).get().await()
            val bookingDetails = result.toObject(BookingDetails::class.java).apply {
                this?.id = result.id
                if(checkIfDateTimeIsInPast(this!!.bookingDate, this.endTime)) {
                    this.status = "Completed"
                }
            }
            Resource.Success(bookingDetails!!)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    /**
     * Retrieves the list of booking details associated with the given email.
     * @param email The email of the user.
     * @return A resource containing the list of booking details.
     */
    override suspend fun getBookingDetailsByEmail(email: String): Resource<List<BookingDetails>> {
        return try {
            val result = db.collection("bookings").whereEqualTo("userEmail", email).get().await()
            val bookingDetails = result.documents.mapNotNull { document ->
                Log.d("BookingDetailRepositoryImpl", document.data.toString())
                try {
                    document.toObject(BookingDetails::class.java)?.apply {
                        id = document.id // Set the document ID here
                        if(checkIfDateTimeIsInPast(this.bookingDate, this.endTime)) {
                            status = "Completed"
                        }
                    }
                } catch (e: Exception) {
                    null
                }
            }
            Resource.Success(bookingDetails)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    /**
     * Cancels the booking with the given booking ID.
     * @param bookingId The ID of the booking.
     * @return A resource containing the result of the operation.
     */
    override suspend fun cancelBooking(bookingId: String): Resource<String> {
        return try {
           val documnet =  db.collection("bookings").document(bookingId).update("status", "Cancelled").await()
            Log.d("BookingDetailRepositoryImpl", "Document updated with ID: ${documnet}")
            Resource.Success("Booking cancelled successfully")
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }
}