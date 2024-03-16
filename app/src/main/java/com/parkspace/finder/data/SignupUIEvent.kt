package com.parkspace.finder.data

sealed  class SignupUIEvent {
    data class NameChanged(val name: String) : SignupUIEvent()
    data class EmailChanged(val email: String) : SignupUIEvent()
    data class PasswordChanged(val password: String) : SignupUIEvent()

    object SignUpButtonClicked: SignupUIEvent()

}