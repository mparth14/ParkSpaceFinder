package com.parkspace.finder.data

/**
 * Data class representing the state of the login UI.
 * @property email The email input in the login UI.
 * @property password The password input in the login UI.
 */
data class LoginUIState(
    var email: String = "",
    var password: String = "",
)
