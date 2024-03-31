package com.parkspace.finder.navigation

import BookingScreen
import NotificationScreen
import android.content.Context
import android.os.Build
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.parkspace.finder.FilterSection
import com.parkspace.finder.data.AuthViewModel
import com.parkspace.finder.ui.bookings.ActiveBookingScreen
import com.parkspace.finder.ui.bookings.CancelledBookingScreen
import com.parkspace.finder.ui.bookings.CompletedBookingScreen
import com.parkspace.finder.data.ParkingSpaceRepository
import com.parkspace.finder.data.ParkingSpaceViewModel
import com.parkspace.finder.ui.auth.LoginScreen
import com.parkspace.finder.ui.auth.SignupScreen
import com.parkspace.finder.ui.booking.EnterDetailsContent
import com.parkspace.finder.ui.browse.BrowseScreen
import com.parkspace.finder.ui.favourite.FavouriteScreen
import com.parkspace.finder.ui.bookings.ReviewBookingScreen
import com.parkspace.finder.ui.bookings.BookingDetails

import com.parkspace.finder.ui.locationPermission.LocationPermissionScreen
import com.parkspace.finder.ui.onboarding.OnboardingScreen
import com.parkspace.finder.ui.parkingDetail.ParkingDetailScreen
import com.parkspace.finder.ui.parkingticket.ParkingTicketScreen
import com.parkspace.finder.ui.payment.PaymentScreen
import com.parkspace.finder.ui.payment.PaymentSuccessScreen
import com.parkspace.finder.ui.home.HomeScreen
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
    parkingSpaceViewModel: ParkingSpaceViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUTE_ONBOARDING,
    context: Context
) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val screensToShowNavbar = listOf(Screen.Browse, Screen.Bookings, Screen.Favorites, Screen.Notifications, Screen.Account)
    val selectedScreen = rememberSaveable {
        mutableIntStateOf(0)
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
                val parkingId = it.arguments?.getString("parkingId") ?: "0"
                PaymentScreen(navController = navController, parkingId = parkingId, it)
            }
            composable(ROUTE_PAYMENT_SUCCESS) { backStackEntry ->

                PaymentSuccessScreen(navController = navController, backStackEntry.arguments?.getString("bookingId") ?: "0")
            }

            composable(ROUTE_PARKING_TICKET) { backStackEntry ->
                ParkingTicketScreen(navController = navController, backStackEntry.arguments?.getString("bookingId") ?: "0")
            }

            composable(ROUTE_BROWSE) {
                BrowseScreen(context  = context, navController = navController, parkingSpaceViewModel = parkingSpaceViewModel)
            }
            composable(Screen.Bookings.route) {
                BookingScreen(navController = navController)
            }
            composable("activeBookingScreen/{bookingId}") { backStackEntry ->
                val bookingId = backStackEntry.arguments?.getString("bookingId")
                ActiveBookingScreen(bookingId = bookingId)
            }
            composable("cancelledBookingScreen/{bookingId}") { backStackEntry ->
                val bookingId = backStackEntry.arguments?.getString("bookingId")
                CancelledBookingScreen(bookingId = bookingId)
            }
            composable("completedBookingScreen/{bookingId}") { backStackEntry ->
                val bookingId = backStackEntry.arguments?.getString("bookingId")
                CompletedBookingScreen(bookingId = bookingId)
            }
            composable(Screen.Favorites.route) {
                FavouriteScreen(context = context, navController = navController)
            }
            composable(Screen.Notifications.route) {
                NotificationScreen()
            }
            composable(Screen.Account.route) {
                HomeScreen(navController = navController, viewModel = viewModel)
            }
            composable(
                route = ROUTE_PARKING_DETAIL,
            ) { backStackEntry ->
                val parkingId = backStackEntry.arguments?.getString("parkingId") ?: "0"

                ParkingDetailScreen(
                    navController = navController,
                    parkingId = parkingId,
                )
            }

            composable(ROUTE_REQUEST_LOCATION_PERMISSION) {
                LocationPermissionScreen(navController = navController)
            }
            composable(ROUTE_ENTER_BOOKING_DETAIL_SCREEN) {
                val parkingId = it.arguments?.getString("parkingId") ?: "0"
                EnterDetailsContent(navController = navController, parkingId = parkingId)
            }
            composable(ROUTE_FILTER) {
                FilterSection( parkingSpaceViewModel = parkingSpaceViewModel,navController = navController)
            }
            composable(ROUTE_SEARCH) {
                ParkingBookingScreen(navController = navController,parkingSpaceViewModel = parkingSpaceViewModel)
            }

        }
    }

}

@Composable
fun FavoritesScreen() {
    Text(text = "Favorites")
    // Home screen content
}

@Composable
fun NotificationsScreen() {
    Text(text = "Notifications")
    // Dashboard screen content
}

@Composable
fun AccountScreen() {
    Text(text = "Account")
    // Notifications screen content
}
