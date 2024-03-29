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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.parkspace.finder.R
import com.parkspace.finder.data.AuthViewModel

@Composable
fun HomeScreen(viewModel: AuthViewModel?,
               navController: NavController?
) {
    var name by remember { mutableStateOf("John Doe") }
    var email by remember { mutableStateOf("johndoe@example.com") }
    var phone by remember { mutableStateOf("1234567890") }
    var emergencyContact by remember { mutableStateOf("9876543210") }
    var vehicleMake by remember { mutableStateOf("Toyota") }
    var vehicleModel by remember { mutableStateOf("Camry") }
    var licensePlate by remember { mutableStateOf("ABC1234") }
    var profileImageResource by remember {
        mutableStateOf<Uri?>(Uri.parse("android.resource://com.parkspace.finder/" + R.drawable.extend_time))
    }

    var isEditMode by remember { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            // Update profile image resource with the selected image
            profileImageResource = it
        }
    }
    Column(
        modifier = Modifier
            .padding(horizontal = 50.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Box(
            modifier = Modifier
                .size(120.dp)
                .background(Color.Transparent), // Set background color to transparent
            contentAlignment = Alignment.Center
        ) {
            // Your content here
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color.Gray),
                contentAlignment = Alignment.Center
            ) {
                // Profile image
                profileImageResource?.let { uri ->
                    val painter = rememberAsyncImagePainter(uri)
                    Image(
                        painter = painter,
                        contentDescription = "Profile Image",
                        modifier = Modifier.size(100.dp)
                    )
                }
            }
            if (isEditMode) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_edit_24),
                    contentDescription = "Edit Image",
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .padding(4.dp)
                        .size(24.dp)
                        .align(Alignment.BottomEnd)
                        .clickable {
                            // Launch image selection intent
                            launcher.launch("image/*")
                        }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp)) // Add some spacing between image and text fields

        TextField(
            value = name,
            onValueChange = { if (isEditMode) name = it },
            label = { Text("Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
                enabled = isEditMode,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.surface)
        )

        TextField(
            value = email,
            onValueChange = { if (isEditMode) email = it },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            enabled = false, // Email field should always be non-editable
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.surface)
        )

        TextField(
            value = phone,
            onValueChange = { if (isEditMode) phone = it },
            label = { Text("Phone") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            enabled = isEditMode,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Next
            ),
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.surface)
        )


        TextField(
            value = vehicleMake,
            onValueChange = { if (isEditMode) vehicleMake = it },
            label = { Text("Vehicle Make") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            enabled = isEditMode,
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Sentences,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.surface)
        )

        TextField(
            value = vehicleModel,
            onValueChange = { if (isEditMode) vehicleModel = it },
            label = { Text("Vehicle Model") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            enabled = isEditMode,
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Sentences,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.surface)
        )

        TextField(
            value = licensePlate,
            onValueChange = { if (isEditMode) licensePlate = it },
            label = { Text("License Plate") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            enabled = isEditMode,
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.None,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.surface)
        )
        TextField(
            value = emergencyContact,
            onValueChange = { if (isEditMode) emergencyContact = it },
            label = { Text("Emergency Contact") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            enabled = isEditMode,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Next
            ),
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.surface)
        )
        Button(
            onClick = {
                if (isEditMode) {
                    // Save action
                    isEditMode = false // Switch back to view mode after saving
                } else {
                    isEditMode = true // Switch to edit mode
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isEditMode) "Save" else "Edit")
        }
    }
}