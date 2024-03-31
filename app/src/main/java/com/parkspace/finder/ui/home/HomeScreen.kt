package com.parkspace.finder.ui.home

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.parkspace.finder.R
import com.parkspace.finder.data.AuthViewModel
import com.parkspace.finder.navigation.ROUTE_LOGIN
import com.parkspace.finder.navigation.ROUTE_NOTIFICATIONS
import com.parkspace.finder.navigation.ROUTE_SIGNUP

@Composable
fun HomeScreen(viewModel: AuthViewModel?, navController: NavController?) {
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

        val launcher =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
                uri?.let {
                    // Update profile image resource with the selected image
                    profileImageResource = it
                }
            }

        // Top Section (Surface)
        val dark_blue = colorResource(id = R.color.dark_blue)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp), // Set a specific height for the Box
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = dark_blue,
                shape = RoundedCornerShape(
                    topStart = CornerSize(0.dp),
                    topEnd = CornerSize(0.dp),
                    bottomStart = CornerSize(16.dp),
                    bottomEnd = CornerSize(16.dp)
                ) // Adjust the corner radius as needed
            ) {
                // Content of the Surface
                // You can place the existing content here
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
                            Icon(
                                Icons.Filled.MoreVert,
                                contentDescription = "Menu",
                                tint = Color.White
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Box(
                                modifier = Modifier
                            ) {
                                //profile_picture
                                profileImageResource?.let { uri ->
                                    val painter = rememberAsyncImagePainter(uri)
                                    Image(
                                        painter = painter,
                                        contentDescription = "Profile Image",
                                        modifier = Modifier
                                            .size(100.dp)
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


        //account credit.
        val creditAmount = "$100.00"
        Surface(
            color = colorResource(id = R.color.LightGrey), // Set your desired grey color here
            shape = RoundedCornerShape(16.dp), // Set the corner radius
            modifier = Modifier.fillMaxWidth()
                .padding(
                top = 16.dp,
                start = 16.dp,
                bottom = 16.dp,
                end = 16.dp
            )

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 16.dp,
                        start = 16.dp,
                        bottom = 16.dp,
                        end = 16.dp
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween // Align elements to the start and end
            ) {
                // Account Credit text on the left side
                Text(
                    text = "Account Credit",
                    style = MaterialTheme.typography.body1,
                    color = Color.Black
                )
                // Yellow bordered amount on the right side
                Box(
                    modifier = Modifier
                        .width(75.dp) // Set the width
                        .height(30.dp) // Set the height

                        .background(colorResource(id = R.color.GoldenYellow), shape = RoundedCornerShape(20.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = creditAmount,
                        style = MaterialTheme.typography.body1,
                        color = Color.White
                    )
                }
            }
        }
        //Account Options.
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Column {
                Text(
                    text = "Account",
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .height(50.dp)
                        .padding(8.dp)
                        .fillMaxWidth()
                        .drawWithContent {
                            drawContent()
                            drawLine(
                                color = Color.LightGray,
                                start = Offset(0f, size.height),
                                end = Offset(size.width, size.height),
                                strokeWidth = 1.dp.toPx()
                            )
                        }.clickable {
                            viewModel?.logout()
                            navController?.navigate(ROUTE_SIGNUP) {
                                popUpTo(ROUTE_SIGNUP) {
                                    inclusive = true
                                }
                            }
                        }
                ) {
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .background(
                                color = colorResource(id = R.color.purple_500),
                                shape = RoundedCornerShape(8.dp) // Adjust the corner radius as needed
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.padlock),
                            contentDescription = "Change Password Image",
                            modifier = Modifier
                                .size(22.dp),
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp)) // Add horizontal space

                    Text(
                        text = "Change Password",
                        style = MaterialTheme.typography.body1.copy(fontSize = 18.sp),
                        modifier = Modifier.clickable {
                            viewModel?.logout()
                            navController?.navigate(ROUTE_SIGNUP) {
                                popUpTo(ROUTE_SIGNUP) {
                                    inclusive = true
                                }
                            }
                        }
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .height(50.dp)
                        .padding(8.dp)
                        .fillMaxWidth()
                        .drawWithContent {
                            drawContent()
                            drawLine(
                                color = Color.LightGray,
                                start = Offset(0f, size.height),
                                end = Offset(size.width, size.height),
                                strokeWidth = 1.dp.toPx()
                            )
                        }
                        .clickable {
                            viewModel?.logout()
                            navController?.navigate(ROUTE_NOTIFICATIONS) {
                                popUpTo(ROUTE_NOTIFICATIONS) {
                                    inclusive = true
                                }
                            }
                        }
                ) {
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .background(
                                color = colorResource(id = R.color.purple_500),
                                shape = RoundedCornerShape(8.dp) // Adjust the corner radius as needed
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.bell),
                            contentDescription = "Notification Image",
                            modifier = Modifier
                                .size(22.dp),
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp)) // Add horizontal space

                    Text(
                        text = "Notification",
                        style = MaterialTheme.typography.body1.copy(fontSize = 18.sp),
                        modifier = Modifier.clickable {
                            viewModel?.logout()
                            navController?.navigate(ROUTE_NOTIFICATIONS) {
                                popUpTo(ROUTE_NOTIFICATIONS) {
                                    inclusive = true
                                }
                            }
                        }
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .height(50.dp)
                        .padding(8.dp)
                        .fillMaxWidth()
                        .drawWithContent {
                            drawContent()
                            drawLine(
                                color = Color.LightGray,
                                start = Offset(0f, size.height),
                                end = Offset(size.width, size.height),
                                strokeWidth = 1.dp.toPx()
                            )
                        }
                        .clickable {
                            viewModel?.logout()
                            navController?.navigate(ROUTE_LOGIN) {
                                popUpTo(ROUTE_LOGIN) {
                                    inclusive = true
                                }
                            }
                        }
                ) {
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .background(
                                color = colorResource(id = R.color.purple_500),
                                shape = RoundedCornerShape(8.dp) // Adjust the corner radius as needed
                            ).clickable {
                                viewModel?.logout()
                                navController?.navigate(ROUTE_LOGIN) {
                                    popUpTo(ROUTE_LOGIN) {
                                        inclusive = true
                                    }
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.exit),
                            contentDescription = "Sign Out Image",
                            modifier = Modifier
                                .size(22.dp),
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp)) // Add horizontal space

                    Text(
                        text = "Sign Out",
                        style = MaterialTheme.typography.body1.copy(fontSize = 18.sp),
                    )
                }
            }


        }

    }
}



