package com.parkspace.finder.data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.parkspace.finder.data.utils.await
import javax.inject.Inject

class ParkingSpaceRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
) : ParkingSpaceRepository{
    override suspend fun getParkingSpaces() : Resource<List<ParkingSpace>> {
        return try {
            val snapshot = db.collection("parking-spaces").get().await()
            Log.d("ParkingSpaceRepositoryImpl", snapshot.toString())
            snapshot.documents.forEach {
                Log.d("ParkingSpaceRepositoryImpl", it.data.toString())
            }
            val parkingSpaces = snapshot.toObjects(ParkingSpace::class.java)
            Resource.Success(parkingSpaces)
        } catch (e: Exception) {
            Log.d("error", e.toString())
            Resource.Failure(e)
        }
    }
}