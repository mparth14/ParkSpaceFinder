package com.parkspace.finder.ui.search
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.navigation.NavHostController
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.parkspace.finder.data.AuthViewModel
import com.parkspace.finder.ui.theme.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.util.*
import kotlin.text.Typography



@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun ParkingBookingScreen() {
    //viewModel: AuthViewModel?, navController: NavHostController
    var hint: String = "Search for everything..."
    var text by remember { mutableStateOf("") }
    var isHintDisplayed by remember { mutableStateOf(hint != "") }
    var place by remember { mutableStateOf("Halifax?") }
    var date by remember { mutableStateOf(TextFieldValue()) }
    var time by remember { mutableStateOf(TextFieldValue()) }
    var selectedDate by remember { mutableStateOf(TextFieldValue()) }
    val spacing = MaterialTheme.spacing
    val calenderState = rememberSheetState()

    CalendarDialog(state = calenderState, selection = CalendarSelection.Dates { selectedDate ->
        val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    })

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                text = "The Best Parking Place in",
                modifier = Modifier
                    .padding(16.dp),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyLarge

            )
            Text(
                text = place,
                modifier = Modifier
                    .padding(16.dp, 0.dp, 0.dp, 0.dp),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.primary),
                color = md_theme_light_primary


            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(10.dp))
                    .padding(16.dp)
            ) {
                BasicTextField(
                    value = text,
                    onValueChange = {
                        text = it
                        isHintDisplayed = it.isEmpty()
                    },
                    maxLines = 1,
                    singleLine = true,
                    modifier = Modifier
                        .width(350.dp)
                        .height(50.dp)
                        .align(Alignment.Center)
                        .shadow(5.dp, RoundedCornerShape(10.dp))
                        .background(Color.White)
                        .onFocusChanged {
                            isHintDisplayed = it.isFocused != (text.isNotBlank())
                        }
                )
                if (isHintDisplayed) {
                    Text(
                        text = hint,
                        color = Color.LightGray,
                        modifier = Modifier
                            .padding(horizontal = 40.dp, vertical = 12.dp)
                            .align(Alignment.CenterStart)
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(75.dp)
                    .padding(16.dp)
                    .background(color = md_theme_light_primary.copy(alpha = 0.2f)), // Set the background color with opacity
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Hurry! Book Now ",
                        color = md_theme_light_onSecondarySurfaceTint,
                        fontWeight = FontWeight.Bold // Making the text bold
                    )
                    Text(
                        text = "lots of discounts waiting",
                        color = md_theme_light_onSecondarySurfaceTint,
                        fontWeight = FontWeight.Normal // Keeping the text normal
                    )
                }
            }
            TransportationIcons()

            Spacer(modifier = Modifier.height(15.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column {


                    Text(
                        text = "Booking Details",
                        modifier = Modifier.padding(bottom = 16.dp),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    OutlinedTextField(
                        value = selectedDate,
                        onValueChange = { selectedDate = it },
                        label = { Text("Enter Booking Date") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusEvent { focusState ->
                                if (focusState.isFocused) {
                                    calenderState.show()
                                }
                            },
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = time,
                        onValueChange = { time = it },
                        label = { Text("Enter Time") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
            }

            Text(
                text = "Select Duration",
                modifier = Modifier.padding(16.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            DurationSelector()

            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .width(350.dp)
                    .padding(
                        5.dp
                    )
                    .background(
                        color = md_theme_light_primary,
                        shape = MaterialTheme.shapes.small
                    ),
                colors = ButtonDefaults.buttonColors(
                    md_theme_light_primary
                )


            ) {
                Text(
                    text = "Proceed with Booking",
                )
            }
        }
        }
    }

@Composable
@Preview
fun TransportationIcons() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        TransportationIcon(
            iconVector = Icons.Default.DirectionsCar,
            label = "Car"
        )
        TransportationIcon(
            iconVector = Icons.Default.DirectionsBike,
            label = "Bike"
        )
        TransportationIcon(
            iconVector = Icons.Default.DirectionsBus,
            label = "Bus"
        )
        TransportationIcon(
            iconVector = Icons.Default.FireTruck,
            label = "Truck"
        )
    }
}


@Composable
fun TransportationIcon(
    iconVector: ImageVector,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.background(color = md_theme_light_inverseOnSurface.copy(alpha = 1f),RoundedCornerShape(10.dp)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier
            .size(80.dp)
            .fillMaxSize()
            .padding(bottom = 15.dp),
            contentAlignment = Alignment.Center,

        ) {
            Icon(
                painter = rememberVectorPainter(image = iconVector),
                contentDescription = label,
                modifier = Modifier
                    .size(35.dp),
                tint = md_theme_light_outline
            )
            Text(
                modifier = Modifier
                    .align(Alignment.BottomCenter),
                text = label,
                color = md_theme_light_outline,
                fontSize = 12.sp,
                fontWeight = FontWeight(500)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

    }
}

@Composable
@Preview
fun DurationSelector() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        DurationChip(
            text = "Half Day",
            selected = false, // Set this based on your selection logic
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(16.dp))
        DurationChip(
            text = "Full Day",
            selected = false, // Set this based on your selection logic
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(16.dp))
        DurationChip(
            text = "Multi Day (1-2 days)",
            selected = false, // Set this based on your selection logic
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun DurationChip(
    text: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onChipClick: () -> Unit = {}
) {
    Surface(
        modifier = modifier
            .size(width = 120.dp, height = 80.dp)
            .clickable { onChipClick() }
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        color = if (selected) md_theme_light_surfaceVariant else md_theme_light_background,
        contentColor = if (selected) md_theme_dark_onPrimary else md_theme_light_onPrimary,
    ) {
        Text(

            text = text,
            color = if (selected) md_theme_dark_onPrimary else md_theme_light_secondary,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        )
    }
}


