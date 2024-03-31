/**
 * Implementation of the [AuthRepository] interface for handling authentication operations using Firebase Authentication.
 */
package com.parkspace.finder.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.parkspace.finder.data.utils.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {
    /**
     * Retrieves the current authenticated user.
     */
    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    /**
     * Performs user login with the provided email and password.
     * @param email The email of the user.
     * @param password The password of the user.
     * @return A resource containing the result of the login operation.
     */
    override suspend fun login(email: String, password: String): Resource<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Resource.Success(result.user!!)
        } catch (e: Exception) {
            return Resource.Failure(e)
        }
    }

    /**
     * Performs user signup with the provided email, password, and name.
     * @param email The email of the user.
     * @param password The password of the user.
     * @param name The name of the user.
     * @return A resource containing the result of the signup operation.
     */
    override suspend fun signup(
        email: String,
        password: String,
        name: String
    ): Resource<FirebaseUser> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            result?.user?.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(name).build())?.await()
            Resource.Success(result.user!!)
        } catch (e: Exception) {
            return Resource.Failure(e)
        }
    }

    /**
     * Logs out the current user.
     */
    override fun logout() {
        firebaseAuth.signOut()
    }
}
