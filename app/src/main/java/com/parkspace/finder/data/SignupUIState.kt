package com.parkspace.finder.data

/**
 * Data class representing the state of the signup UI.
 */
data class SignupUIState (
    /**
     * The email entered by the user.
     */
    var email: String = "",

    /**
     * The password entered by the user.
     */
    var password: String = "",

    /**
     * The name entered by the user.
     */
    var name: String = ""
)
