package com.parkspace.finder.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.parkspace.finder.data.AuthViewModel
import com.parkspace.finder.ui.auth.LoginScreen
import com.parkspace.finder.ui.auth.SignupScreen
import com.parkspace.finder.ui.booking.EnterDetailsActivity
import com.parkspace.finder.ui.booking.EnterDetailsContent
import com.parkspace.finder.ui.home.HomeScreen
import com.parkspace.finder.ui.payment.PaymentScreen
import com.parkspace.finder.ui.payment.PaymentSuccessScreen

@Composable
fun AppNavHost(
    viewModel: AuthViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUTE_ENTER_DETAILS_CONTENT
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(ROUTE_LOGIN) {
            LoginScreen(viewModel, navController = navController)
        }
        composable(ROUTE_SIGNUP) {
            SignupScreen(viewModel, navController = navController)
        }
        composable(ROUTE_HOME) {
            HomeScreen(viewModel, navController = navController)
        }
        composable(ROUTE_PAYMENT) {
            PaymentScreen(navController = navController)
        }
        composable(ROUTE_PAYMENT_SUCCESS) {
            PaymentSuccessScreen(navController = navController, onViewTicketClick = {})
        }
        composable(ROUTE_ENTER_DETAILS_CONTENT) {
            EnterDetailsContent(navController = navController, onViewTicketClick = {})
        }

    }
}
