package com.parkspace.finder.data

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel(){
    private val _loginFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val loginFlow:StateFlow<Resource<FirebaseUser>?> = _loginFlow

    private val _signupFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val signupFlow:StateFlow<Resource<FirebaseUser>?> = _signupFlow

    var signupUIState = mutableStateOf(SignupUIState())
    var loginUIState = mutableStateOf(LoginUIState())

    val currentUser: FirebaseUser?
    get() = repository.currentUser

    init {
        if (repository.currentUser != null){
            _loginFlow.value = Resource.Success(repository.currentUser!!)
        }
    }

    fun onSignupEvent(uiEvent: SignupUIEvent){
        when(uiEvent){
            is SignupUIEvent.NameChanged -> {
                signupUIState.value = signupUIState.value.copy(name = uiEvent.name)
            }
            is SignupUIEvent.EmailChanged -> {
                signupUIState.value = signupUIState.value.copy(email = uiEvent.email)
            }
            is SignupUIEvent.PasswordChanged -> {
                signupUIState.value = signupUIState.value.copy(password = uiEvent.password)
            }
            is SignupUIEvent.SignUpButtonClicked -> {
                signup()
            }
            else -> {
                // Do nothing
            }
        }
    }

    fun onLoginEvent(uiEvent: LoginUIEvent){
        when(uiEvent){
            is LoginUIEvent.EmailChanged -> {
                loginUIState.value = loginUIState.value.copy(email = uiEvent.email)
            }
            is LoginUIEvent.PasswordChanged -> {
                loginUIState.value = loginUIState.value.copy(password = uiEvent.password)
            }
            is LoginUIEvent.LoginButtonClicked -> {
                login()
            }
            else -> {
                // Do nothing
            }
        }
    }

    private fun login() = viewModelScope.launch {
        _loginFlow.value = Resource.Loading
        val result = repository.login(loginUIState.value.email, loginUIState.value.password)
        _loginFlow.value = result
    }
    private fun signup() = viewModelScope.launch {
        _signupFlow.value = Resource.Loading
        val result = repository.signup(signupUIState.value.email, signupUIState.value.password, signupUIState.value.name)
        _signupFlow.value = result
    }

    fun logout() {
        repository.logout()
        _loginFlow.value = null
        _signupFlow.value = null
    }


}