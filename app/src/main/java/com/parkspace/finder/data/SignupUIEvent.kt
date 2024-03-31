package com.parkspace.finder.data

/**
 * Sealed class representing different events in the signup UI.
 */
sealed class SignupUIEvent {
    /**
     * Represents a change in the user's name input.
     */
    data class NameChanged(val name: String) : SignupUIEvent()

    /**
     * Represents a change in the user's email input.
     */
    data class EmailChanged(val email: String) : SignupUIEvent()

    /**
     * Represents a change in the user's password input.
     */
    data class PasswordChanged(val password: String) : SignupUIEvent()

    /**
     * Represents a click event on the signup button.
     */
    object SignUpButtonClicked: SignupUIEvent()
}
