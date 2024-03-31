/**
 * Repository interface for handling authentication related operations.
 */
package com.parkspace.finder.data

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    /**
     * Retrieves the current authenticated user.
     */
    val currentUser: FirebaseUser?

    /**
     * Performs user login with the provided email and password.
     * @param email The email of the user.
     * @param password The password of the user.
     * @return A resource containing the result of the login operation.
     */
    suspend fun login(email: String, password: String): Resource<FirebaseUser>

    /**
     * Performs user signup with the provided email, password, and name.
     * @param email The email of the user.
     * @param password The password of the user.
     * @param name The name of the user.
     * @return A resource containing the result of the signup operation.
     */
    suspend fun signup(email: String, password: String, name: String): Resource<FirebaseUser>

    /**
     * Logs out the current user.
     */
    fun logout()
}
