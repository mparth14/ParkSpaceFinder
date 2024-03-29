package com.parkspace.finder.data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.parkspace.finder.data.utils.await
import javax.inject.Inject

class BookingDetailRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
) : BookingDetailRepository {
    override suspend fun makeBooking(bookingDetails: BookingDetails): Resource<String> {
        return try {
            val savedData = db.collection("bookings").add(bookingDetails).await()
            Resource.Success(savedData.id)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }
    override suspend fun getBookingDetail(bookingId: String): Resource<BookingDetails> {
        return try {
            Log.d("BookingDetailRepository", "getBookingDetail: $bookingId")
            val result = db.collection("bookings").document(bookingId).get().await()
            Log.d("BookingDetailRepository", "getBookingDetail result: $result")
            val bookingDetails = result.toObject(BookingDetails::class.java).apply {
                this?.id = result.id
            }
            Log.d("BookingDetailRepository", "getBookingDetail success: $bookingDetails")
            Resource.Success(bookingDetails!!)
        } catch (e: Exception) {
            Log.d("BookingDetailRepository", "getBookingDetail: ${e.message}")
            Resource.Failure(e)
        }
    }
}