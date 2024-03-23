package com.parkspace.finder.navigation

import android.util.Log
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.parkspace.finder.data.AuthViewModel
import com.parkspace.finder.ui.auth.LoginScreen
import com.parkspace.finder.ui.auth.SignupScreen
import com.parkspace.finder.ui.home.HomeScreen
import com.parkspace.finder.ui.parkingticket.ParkingTicketScreen
import com.parkspace.finder.ui.payment.PaymentScreen
import com.parkspace.finder.ui.payment.PaymentSuccessScreen
import com.parkspace.finder.ui.favourite.FavouriteScreen


sealed class Screen(val route: String, val icon: ImageVector?, val selectedIcon: ImageVector?, val title: String) {
    object Browse : Screen("browse", Icons.Outlined.Search, Icons.Filled.Search, "Browse")
    object Bookings : Screen("bookings", Icons.Outlined.Info, Icons.Filled.Info, "Bookings")
    object Favorites : Screen("favorites", Icons.Outlined.Favorite, Icons.Filled.Favorite,"Favorite")
    object Notifications : Screen("notifications", Icons.Outlined.Notifications, Icons.Filled.Notifications, "Notifications")
    object Account : Screen("account", Icons.Outlined.AccountCircle, Icons.Filled.AccountCircle, "Account")
}
@Composable
fun AppNavHost(
    viewModel: AuthViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUTE_LOGIN
) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val screensToShowNavbar = listOf(Screen.Browse, Screen.Bookings, Screen.Favorites, Screen.Notifications, Screen.Account)
    var selectedScreen = rememberSaveable {
        mutableStateOf(0)
    }
    val currentRoute = navBackStackEntry.value?.destination?.route
    Log.d("AppNavHost", "currentRoute: $currentRoute")
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
    ) {

    }
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
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
        composable(ROUTE_PAYMENT_SUCCESS) {
            PaymentSuccessScreen(navController = navController, onViewTicketClick = {})
        }
        composable(ROUTE_PARKING_TICKET) {
            ParkingTicketScreen(
                startTime = "10:00 PM",
                endTime = "12:00 PM",
                spotNumber = "B20",
                duration = "2 hours",
                onBackClicked = { navController.popBackStack() }
            )
        }
        composable(Screen.Browse.route) {
            HomeScreen(viewModel,navController = navController)
        }
        composable(Screen.Bookings.route) {
            BookignsScreen(navController = navController)
        }
        composable(Screen.Favorites.route) {
            FavouriteScreen(viewModel,navController = navController)
        }
        composable(Screen.Notifications.route) {
            NotifactionsScreen()
        }
        composable(Screen.Account.route) {
            AccontScreen()
        }
    }
}

@Composable
fun BrowseScreen(navController: NavHostController) {
    Text(text = "Browse")
    // Login screen content
    // Navigate to HomeScreen after successful login
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