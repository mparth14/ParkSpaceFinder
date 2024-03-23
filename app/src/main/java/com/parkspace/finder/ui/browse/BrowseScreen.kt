package com.parkspace.finder.ui.browse

import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.parkspace.finder.data.ParkingSpaceViewModel
import com.parkspace.finder.data.Resource
import com.parkspace.finder.navigation.ROUTE_REQUEST_LOCATION_PERMISSION
import kotlinx.coroutines.delay

@Composable
@ExperimentalMaterial3Api
fun BrowseScreen(context: Context, parkingSpaceViewModel: ParkingSpaceViewModel = hiltViewModel(), navController: NavHostController) {
    val permissions = arrayOf(
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )

    if(!permissions.all {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }){
        navController.popBackStack()
        navController.navigate(ROUTE_REQUEST_LOCATION_PERMISSION)
    }else{

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
    Box(Modifier.nestedScroll(state.nestedScrollConnection)) {
    parkingSpaces.value.let {
        when(it){
            is Resource.Success -> {
                Log.d("BrowseScreen", it.result.toString())
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(it.result!!.size) { index ->
                        ParkingLotItem(navController = navController, parkingSpace = it.result[index])
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