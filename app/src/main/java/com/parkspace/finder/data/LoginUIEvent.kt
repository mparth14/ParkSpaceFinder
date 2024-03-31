package com.parkspace.finder.data

/**
 * Sealed class representing events related to login UI.
 */
sealed class LoginUIEvent {
    /**
     * Event representing a change in email input.
     * @param email The new email input.
     */
    data class EmailChanged(val email: String) : LoginUIEvent()

    /**
     * Event representing a change in password input.
     * @param password The new password input.
     */
    data class PasswordChanged(val password: String) : LoginUIEvent()

    /**
     * Event representing a click on the login button.
     */
    object LoginButtonClicked : LoginUIEvent()
}