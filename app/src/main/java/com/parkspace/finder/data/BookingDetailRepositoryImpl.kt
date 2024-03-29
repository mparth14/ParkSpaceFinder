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
            val result = db.collection("bookings").document(bookingId).get().await()
            val bookingDetails = result.toObject(BookingDetails::class.java).apply {
                this?.id = result.id
            }
            Resource.Success(bookingDetails!!)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun getBookingDetailsByEmail(email: String): Resource<List<BookingDetails>> {
        return try {
            val result = db.collection("bookings").whereEqualTo("userEmail", email).get().await()
//            val bookingDetails = result.toObjects(BookingDetails::class.java).map {
//                it.apply {
//                    id = this.id
//                }
//            }
            val bookingDetails = result.documents.mapNotNull { document ->
                Log.d("ParkingSpaceRepositoryImpl", document.data.toString())
                try {
                    document.toObject(BookingDetails::class.java)?.apply {
                        id = document.id // Set the document ID here
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
}