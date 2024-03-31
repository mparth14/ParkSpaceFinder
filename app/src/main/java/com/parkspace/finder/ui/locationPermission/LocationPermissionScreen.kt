package com.parkspace.finder.ui.locationPermission

import android.content.res.Resources.Theme
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.parkspace.finder.R
import com.parkspace.finder.data.ParkingSpaceViewModel
import com.parkspace.finder.navigation.ROUTE_BROWSE

@Composable
fun LocationPermissionScreen(navController: NavHostController, parkingSpaceViewModel: ParkingSpaceViewModel) {
    val context = LocalContext.current
    val permissions = arrayOf(
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )

    val launchMultiplePermissions = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestMultiplePermissions()){
        permissionMap ->
        val areGranted = permissionMap.values.reduce{acc, next -> acc && next}
        if(areGranted){
            // Navigate to the next screen
            parkingSpaceViewModel.setLocationPermissionGranted()
            navController?.popBackStack()
            navController?.navigate(ROUTE_BROWSE)
        }else{
            Toast.makeText(context, "Permissions denied", Toast.LENGTH_SHORT).show()
        }
    }
    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 48.dp)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset((-24).dp)
            ) {
                Icon(
                    painter = painterResource(
                        R.drawable.location_24
                    ),
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = "Location Permission",
                    modifier = Modifier.size(120.dp)
                )
            }
            Text(
                text = "Hello, nice to meet you!",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold, fontSize = 32.sp
                ),
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Set your location to start finding parking space around you",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                          launchMultiplePermissions.launch(permissions)
                }, colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary, contentColor = Color.White
                ),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.my_location_24),
                    contentDescription = "Current location arrow",
                    tint = Color.White
                )
                Text(
                    text = "Use current location",
                    modifier = Modifier.padding(start = 8.dp),
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "We only access your location while you are using this incredible app",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}