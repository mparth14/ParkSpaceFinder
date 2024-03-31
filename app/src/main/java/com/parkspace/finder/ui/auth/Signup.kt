package com.parkspace.finder.ui.auth

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.parkspace.finder.R
import com.parkspace.finder.data.AuthViewModel
import com.parkspace.finder.data.Resource
import com.parkspace.finder.data.SignupUIEvent
import com.parkspace.finder.navigation.ROUTE_BROWSE
import com.parkspace.finder.navigation.ROUTE_HOME
import com.parkspace.finder.navigation.ROUTE_LOGIN
import com.parkspace.finder.ui.theme.spacing


/**
 * Composable function for the sign-up screen.
 *
 * @param viewModel: Instance of AuthViewModel for handling authentication-related operations.
 * @param navController: NavHostController for navigating between composables.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(viewModel: AuthViewModel?, navController: NavHostController) {
    var name = remember { mutableStateOf("") }
    var email = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }

    val signupFlow = viewModel?.signupFlow?.collectAsState()

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (refHeader, refName, refEmail, refPassword, refButtonSignup, refTextSignup) = createRefs()
        val spacing = MaterialTheme.spacing

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Column(modifier = Modifier.padding(top = spacing.extraLarge)) {
                    AuthHeader("Welcome \nuser")
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(top = spacing.small, start = spacing.large),
                        text = "Sign up to join",
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Left,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize()
                        .padding(
                            start = spacing.large,
                            end = spacing.large
                        )
                ) {
                    TextField(
                        value = name.value,
                        onValueChange = {
                            name.value = it
                            viewModel?.onSignupEvent(SignupUIEvent.NameChanged(it))
                        },
                        label = {
                            Text(text = stringResource(id = R.string.name))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = spacing.large,
                            ),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.None,
                            autoCorrect = false,
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        )
                    )

                    TextField(
                        value = email.value,
                        onValueChange = {
                            email.value = it
                            viewModel?.onSignupEvent(SignupUIEvent.EmailChanged(it))
                        },
                        label = {
                            Text(text = stringResource(id = R.string.email))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = spacing.medium,
                            ),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.None,
                            autoCorrect = false,
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        )
                    )

                    TextField(
                        value = password.value,
                        onValueChange = {
                            password.value = it
                            viewModel?.onSignupEvent(SignupUIEvent.PasswordChanged(it))
                        },
                        label = {
                            Text(text = stringResource(id = R.string.password))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = spacing.medium,
                            ),
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.None,
                            autoCorrect = false,
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        )
                    )

                    Button(
                        onClick = {
                            viewModel?.onSignupEvent(SignupUIEvent.SignUpButtonClicked)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(
                                top = spacing.small,
                            ),
                        shape = MaterialTheme.shapes.small
                    ) {
                        when(signupFlow?.value){
                            is Resource.Loading -> {
                                CircularProgressIndicator(
                                        color = Color.White,
                                        modifier = Modifier.size(28.dp)
                                )
                            }
                            else -> {
                                Text(
                                        text = stringResource(id = R.string.signup),
                                        style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing.large),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier
                        .clickable {
                            navController.navigate(ROUTE_LOGIN)
                        },
                    text = stringResource(id = R.string.already_have_account),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

    }
    signupFlow?.value?.let {
        when(it){
            is Resource.Failure -> {
                val context = LocalContext.current
                Toast.makeText(context, it.exception.message, Toast.LENGTH_SHORT).show()
            }
            is Resource.Success -> {
                LaunchedEffect(Unit){
                    navController.navigate(ROUTE_BROWSE){
                        popUpTo(ROUTE_BROWSE) {
                            inclusive = true
                        }
                    }
                }
            }
            else -> {}
        }
    }
}
