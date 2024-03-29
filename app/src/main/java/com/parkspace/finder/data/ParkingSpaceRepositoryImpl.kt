package com.parkspace.finder.data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.parkspace.finder.data.utils.await
import javax.inject.Inject

class ParkingSpaceRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
) : ParkingSpaceRepository {
    override suspend fun getParkingSpaces(): Resource<List<ParkingSpace>> {
        return try {
            val snapshot = db.collection("parking-spaces").get().await()
            val parkingSpaces = snapshot.documents.mapNotNull { document ->
                Log.d("ParkingSpaceRepositoryImpl", document.data.toString())
                try {
                    document.toObject(ParkingSpace::class.java)?.apply {
                        id = document.id // Set the document ID here
                    }
                } catch (e: Exception) {
                    Log.d("error", "Error converting document to ParkingSpace: ${e.message}")
                    null
                }
            }
            Resource.Success(parkingSpaces)
        } catch (e: Exception) {
            Log.d("error", e.toString())
            Resource.Failure(e)
        }
    }

    override suspend fun getParkingSpaceByName(name: String): ParkingSpace? {
        return try {
            val querySnapshot = db.collection("parking-spaces")
                .whereEqualTo("name", name)
                .get()
                .await()

            if (!querySnapshot.isEmpty) {
                val documentSnapshot = querySnapshot.documents[0]
                documentSnapshot.toObject(ParkingSpace::class.java)
            } else {
                null // Parking space with the specified name not found
            }
        } catch (e: Exception) {
            Log.e("ParkingSpaceRepository", "Error getting parking space by name: $name", e)
            null
        }
    }
}