package com.parkspace.finder.ui.browse

import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Space
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.firebase.annotations.concurrent.Background
import com.parkspace.finder.R
import com.parkspace.finder.data.ParkingSpaceViewModel
import com.parkspace.finder.data.Resource
import com.parkspace.finder.navigation.ROUTE_REQUEST_LOCATION_PERMISSION
import kotlinx.coroutines.delay

@Composable
fun AddressBar(address: String) {
    Row {
        Icon(
            painter = painterResource(id = R.drawable.location_24),
            contentDescription = "Location icon",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .size(54.dp)
                .padding(end = 8.dp)
        )
        Column {
            Text(text = "Your Location", fontSize = MaterialTheme.typography.bodySmall.fontSize, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(text = address, fontSize = MaterialTheme.typography.bodyMedium.fontSize, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
@ExperimentalMaterial3Api
fun BrowseScreen(
    context: Context,
    parkingSpaceViewModel: ParkingSpaceViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val permissions = arrayOf(
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )

    val needsLocationPermission = parkingSpaceViewModel.needsLocationPermission.collectAsState()
    val currentLocation = parkingSpaceViewModel.currentLocation.collectAsState()
    val addresses = parkingSpaceViewModel.addresses.collectAsState()

    if (needsLocationPermission.value) {
        navController.popBackStack()
        navController.navigate(ROUTE_REQUEST_LOCATION_PERMISSION)
    }

    val parkingSpaces = parkingSpaceViewModel.parkingSpaces.collectAsState()
    val state = rememberPullToRefreshState()
    if (state.isRefreshing) {
        LaunchedEffect(true) {
            // fetch something
            delay(1500)
            parkingSpaceViewModel.fetchParkingSpaces()
            state.endRefresh()
        }
    }
    Column {
        Surface(
            modifier = Modifier.zIndex(1f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(top = 16.dp, bottom = 4.dp),
            ) {
                if (addresses.value.isNotEmpty()) {
                    AddressBar(address = addresses.value[0].getAddressLine(0))
                }
                Text(text = "Showing parking lots near you", modifier = Modifier.padding(start = 16.dp, end=16.dp), fontSize = MaterialTheme.typography.headlineSmall.fontSize, fontWeight = FontWeight.Bold)
            }
        }
        Box(
            Modifier
                .nestedScroll(state.nestedScrollConnection)
                .padding(start = 16.dp, end = 16.dp)) {
            parkingSpaces.value.let {
                when (it) {
                    is Resource.Success -> {
                        Column {
                            LazyColumn(modifier = Modifier.fillMaxSize().padding(bottom = 80.dp)) {
                                items(it.result!!.size) { index ->
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 16.dp)
                                    ){
                                        ParkingLotItem(
                                            navController = navController,
                                            parkingSpace = it.result[index],
                                        )
                                    }

                                }
                            }
                        }
                    }
                    is Resource.Failure -> {
                        Log.d("BrowseScreen", it.exception.message.toString())
                        val context = LocalContext.current
                        Toast.makeText(context, it.exception.message, Toast.LENGTH_SHORT).show()
                    }

                    is Resource.Loading -> {
                        Text(text = "Loading...")
                    }

                    else -> {
                        Text(text = "Error")
                    }
                }
            }
            PullToRefreshContainer(
                modifier = Modifier.align(Alignment.TopCenter),
                state = state,
            )
        }
    }
}