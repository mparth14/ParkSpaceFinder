package com.parkspace.finder.ui.home

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.parkspace.finder.R
import com.parkspace.finder.data.AuthViewModel

@Composable
fun HomeScreen(viewModel: AuthViewModel?,
               navController: NavController?) {
    Column(modifier = Modifier.fillMaxSize()) {
        var name by remember { mutableStateOf(viewModel?.currentUser?.displayName ?: "") }
        var email by remember { mutableStateOf(viewModel?.currentUser?.email ?: "") }
        var phone by remember { mutableStateOf("") }
        var emergencyContact by remember { mutableStateOf("") }
        var vehicleMake by remember { mutableStateOf("") }
        var vehicleModel by remember { mutableStateOf("") }
        var licensePlate by remember { mutableStateOf("") }
        var profileImageResource by remember {
            mutableStateOf<Uri?>(Uri.parse("android.resource://com.parkspace.finder/" + R.drawable.user_profile))
        }

        val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                // Update profile image resource with the selected image
                profileImageResource = it
            }
        }



        // Top Section (replace with TopAppBar or Surface)
        val dark_blue = colorResource(id = R.color.dark_blue)
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 600.dp), // Remove top padding
            color = dark_blue,
            shape = RoundedCornerShape(
                topStart = CornerSize(0.dp),
                topEnd = CornerSize(0.dp),
                bottomStart = CornerSize(16.dp),
                bottomEnd = CornerSize(16.dp)
            ) // Adjust the corner radius as needed
        ) {
           //add the three dot menu
            Column(modifier = Modifier.fillMaxSize()) {


                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.TopEnd
                ) {

                    //three dot menu
                    IconButton(
                        onClick = { /* Handle menu click */ },
                    ) {
                        Icon(Icons.Filled.MoreVert, contentDescription = "Menu", tint = Color.White)
                    }
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.Bottom
                    ) {                    Box(
                        modifier = Modifier
                    ) {
                        //profile_picture
                        profileImageResource?.let { uri ->
                            val painter = rememberAsyncImagePainter(uri)
                            Image(
                                painter = painter,
                                contentDescription = "Profile Image",
                                modifier = Modifier.size(100.dp)
                                    .clip(CircleShape),// Apply circular clipping
                            )
                        }
                        //edit image
                        Icon(
                            painter = painterResource(id = R.drawable.edit_image_icon),
                            contentDescription = "Edit Image",
                            tint = Color.Unspecified,
                            modifier = Modifier
                                .padding(start = 4.dp, bottom = 4.dp, end = 0.dp)
                                .size(24.dp)
                                .align(Alignment.BottomEnd)
                                .clickable {
                                    // Launch image selection intent
                                    launcher.launch("image/*")
                                }
                                .clip(CircleShape) // Apply circular clipping

                        )

                    }

                    //box  - place its elements in a column
                    //add_name
                    //add_email
                    Column(
                        modifier = Modifier.padding(top = 110.dp, start = 20.dp)
                    ) {
                        // Add name text
                        Text(
                            text = viewModel?.currentUser?.displayName ?: "",

                            style = MaterialTheme.typography.h6,
                            color = Color.White // Changed to onSurface
                        )
                        // Add email text
                        Text(
                            text = viewModel?.currentUser?.email ?: "",
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier.weight(0.7f),
                            color = Color.White // Changed to onSurface
                        )
                    }
                }
                }
                }
            }
        }
    }

