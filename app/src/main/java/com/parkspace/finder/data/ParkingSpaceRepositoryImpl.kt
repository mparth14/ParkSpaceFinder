package com.parkspace.finder.data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
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
    override suspend fun getParkingSpacesSortedByPriceHighToLow(): Resource<List<ParkingSpace>> {
        return try {
            val snapshot = db.collection("parking-spaces")
                .orderBy("hourlyPrice", Query.Direction.DESCENDING) // Sort by price high to low
                .get()
                .await()
            val parkingSpaces = snapshot.toObjects(ParkingSpace::class.java)
            Resource.Success(parkingSpaces)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun getParkingSpacesSortedByPriceLowToHigh(): Resource<List<ParkingSpace>> {
        return try {
            val snapshot = db.collection("parking-spaces")
                .orderBy("hourlyPrice", Query.Direction.ASCENDING) // Sort by price high to low
                .get()
                .await()
            val parkingSpaces = snapshot.toObjects(ParkingSpace::class.java)
            Resource.Success(parkingSpaces)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }
    override suspend fun getParkingSpacesSortedByRating(descending: Boolean): Resource<List<ParkingSpace>> {
        return try {
            val snapshot = db.collection("parking-spaces")
                .orderBy("rating", if (descending) Query.Direction.DESCENDING else Query.Direction.ASCENDING)
                .get()
                .await()
            val parkingSpaces = snapshot.toObjects(ParkingSpace::class.java)
            Resource.Success(parkingSpaces)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun searchParkingSpaces(query: String): Resource<List<ParkingSpace>> {
        return try {
            val trimmedQuery = query.trim().lowercase() // Trim and convert to lowercase
            val startAtValue = trimmedQuery // Start searching from the given query
            val endAtValue = trimmedQuery + '\uf8ff' // End searching at the query plus a high Unicode value

            val snapshot = db.collection("parking-spaces")
                .orderBy("name") // Order by name
                .startAt(startAtValue) // Start searching from the given query
                .endAt(endAtValue) // End searching at the query plus a high Unicode value
                .get()
                .await()

            val parkingSpaces = snapshot.toObjects(ParkingSpace::class.java)
            Log.d("ParkingSpaceRepositoryImpl", parkingSpaces.toString())
            Resource.Success(parkingSpaces)
        } catch (e: Exception) {
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

    override suspend fun getParkingSpaceById(id: String): Resource<ParkingSpace?> {
        return try {
            val documentSnapshot = db.collection("parking-spaces")
                .document(id)
                .get()
                .await()
            Log.d("documentSnapshot", documentSnapshot.data.toString())
            val parkingSpace = documentSnapshot.toObject(ParkingSpace::class.java)?.apply {
                this.id = documentSnapshot.id // Set the document ID here
            }
            Resource.Success(parkingSpace)
        } catch (e: Exception) {
            Log.e("ParkingSpaceRepository", "Error getting parking space by id: $id", e)
            Resource.Failure(e)
        }
    }
}