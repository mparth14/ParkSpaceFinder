package com.parkspace.finder.ui.browse

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.parkspace.finder.data.ParkingSpaceViewModel
import com.parkspace.finder.data.Resource

@Composable
fun BrowseScreen(navController: NavController, parkingSpaceViewModel: ParkingSpaceViewModel) {
    parkingSpaceViewModel.parkingSpaces.value.let {
        when(it){
            is Resource.Success -> {
                Log.d("BrowseScreen", it.result.toString())
                LazyColumn {
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
}