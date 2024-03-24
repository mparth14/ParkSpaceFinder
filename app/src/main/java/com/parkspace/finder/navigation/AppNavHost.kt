package com.parkspace.finder.navigation

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.parkspace.finder.data.AuthViewModel
import com.parkspace.finder.data.ParkingSpaceViewModel
import com.parkspace.finder.ui.auth.LoginScreen
import com.parkspace.finder.ui.auth.SignupScreen
import com.parkspace.finder.ui.browse.BrowseScreen
import com.parkspace.finder.ui.home.HomeScreen
import com.parkspace.finder.ui.locationPermission.LocationPermissionScreen
import com.parkspace.finder.ui.onboarding.OnboardingScreen
import com.parkspace.finder.ui.parkingDetail.ParkingDetailScreen
import com.parkspace.finder.ui.parkingticket.ParkingTicketScreen
import com.parkspace.finder.ui.payment.BookingDetails
import com.parkspace.finder.ui.payment.PaymentScreen
import com.parkspace.finder.ui.payment.PaymentSuccessScreen
import com.parkspace.finder.ui.search.ParkingBookingScreen


sealed class Screen(val route: String, val icon: ImageVector?, val selectedIcon: ImageVector?, val title: String) {
    object Browse : Screen("browse", Icons.Outlined.Search, Icons.Filled.Search, "Browse")
    object Bookings : Screen("bookings", Icons.Outlined.Info, Icons.Filled.Info, "Bookings")
    object Favorites : Screen("favorites", Icons.Outlined.Favorite, Icons.Filled.Favorite,"Favorite")
    object Notifications : Screen("notifications", Icons.Outlined.Notifications, Icons.Filled.Notifications, "Notifications")
    object Account : Screen("account", Icons.Outlined.AccountCircle, Icons.Filled.AccountCircle, "Account")
}
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavHost(
    viewModel: AuthViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUTE_ONBOARDING,
    context: Context
) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val screensToShowNavbar = listOf(Screen.Browse, Screen.Bookings, Screen.Favorites, Screen.Notifications, Screen.Account)
    var selectedScreen = rememberSaveable {
        mutableStateOf(0)
    }
    val currentRoute = navBackStackEntry.value?.destination?.route
    Scaffold (
        bottomBar = {
            if(screensToShowNavbar.any { it.route == currentRoute }){
                NavigationBar {
                    screensToShowNavbar.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = selectedScreen.value == index,
                            onClick = {
                                selectedScreen.value = index
                                navController.navigate(item.route)
                            },
                            label = {
                                Text(item.title,
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp), // Decrease font size here
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis)
                            },
                            icon = {
                                BadgedBox(badge = {

                                }) {
                                    Icon(
                                        imageVector = if (selectedScreen.value == index) item.selectedIcon!! else item.icon!!,
                                        contentDescription = item.title
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = modifier
        ) {
            composable(ROUTE_ONBOARDING) {
                OnboardingScreen(navController = navController)
            }
            composable(ROUTE_LOGIN) {
                LoginScreen(viewModel ,navController = navController)
            }
            composable(ROUTE_SIGNUP) {
                SignupScreen(viewModel, navController = navController)
            }
            composable(ROUTE_HOME) {
                HomeScreen(viewModel,navController = navController)
            }
            composable(ROUTE_PAYMENT) {
                PaymentScreen(navController = navController)
            }
            composable(
                route = "$ROUTE_PAYMENT_SUCCESS/{startTime}/{endTime}/{spotNumber}/{duration}/{price}/{lotName}",
                arguments = listOf(
                    navArgument("startTime") { type = NavType.StringType },
                    navArgument("endTime") { type = NavType.StringType },
                    navArgument("spotNumber") { type = NavType.StringType },
                    navArgument("duration") { type = NavType.StringType },
                    navArgument("price") { type = NavType.FloatType },
                    navArgument("lotName") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val bookingDetails = BookingDetails(
                    startTime = backStackEntry.arguments?.getString("startTime") ?: "",
                    endTime = backStackEntry.arguments?.getString("endTime") ?: "",
                    spotNumber = backStackEntry.arguments?.getString("spotNumber") ?: "",
                    duration = backStackEntry.arguments?.getString("duration") ?: "",
                    price = backStackEntry.arguments?.getDouble("price") ?: 0.00,
                    lotName = backStackEntry.arguments?.getString("lotName") ?: "",
                )
                PaymentSuccessScreen(navController = navController, bookingDetails = bookingDetails) {
                }
            }

            composable(
                route = "$ROUTE_PARKING_TICKET/{startTime}/{endTime}/{spotNumber}/{duration}/{price}/{lotName}",
                arguments = listOf(
                    navArgument("startTime") { type = NavType.StringType },
                    navArgument("endTime") { type = NavType.StringType },
                    navArgument("spotNumber") { type = NavType.StringType },
                    navArgument("duration") { type = NavType.StringType },
                    navArgument("price") { type = NavType.FloatType },
                    navArgument("lotName") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                ParkingTicketScreen(
                    startTime = backStackEntry.arguments?.getString("startTime") ?: "",
                    endTime = backStackEntry.arguments?.getString("endTime") ?: "",
                    spotNumber = backStackEntry.arguments?.getString("spotNumber") ?: "",
                    duration = backStackEntry.arguments?.getString("duration") ?: "",
                    price = backStackEntry.arguments?.getDouble("price") ?: 0.0,
                    lotName = backStackEntry.arguments?.getString("lotName") ?: "",
                    onBackClicked = { navController.popBackStack() }
                )
            }

            composable(ROUTE_BROWSE) {
                BrowseScreen(context  = context, navController = navController)
            }
            composable(Screen.Bookings.route) {
                BookignsScreen(navController = navController)
            }
            composable(Screen.Favorites.route) {
                FavoritesScreen()
            }
            composable(Screen.Notifications.route) {
                NotifactionsScreen()
            }
            composable(Screen.Account.route) {
                HomeScreen(navController = navController, viewModel = viewModel)
            }
            composable(ROUTE_PARKING_DETAIL) {
                ParkingDetailScreen(navController = navController)
            }
            composable(ROUTE_REQUEST_LOCATION_PERMISSION) {
                LocationPermissionScreen(navController = navController)
            }
        }
    }

}
@Composable
fun BookignsScreen(navController: NavHostController) {
    Text(text = "Bookings")
    // Payment screen content
    // Navigate back to the previous screen after completing the payment
}

@Composable
fun FavoritesScreen() {
    Text(text = "Favorites")
    // Home screen content
}

@Composable
fun NotifactionsScreen() {
    Text(text = "Notifications")
    // Dashboard screen content
}

@Composable
fun AccontScreen() {
    Text(text = "Account")
    // Notifications screen content
}