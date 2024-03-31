package com.parkspace.finder

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.material3.Button
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.parkspace.finder.ui.theme.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.parkspace.finder.data.FilterOptions
import com.parkspace.finder.data.ParkingSpaceViewModel


@Composable
fun FilterSection(parkingSpaceViewModel: ParkingSpaceViewModel,
                  navController: NavController) {
// viewModel: AuthViewModel?, navController: NavHostController
    val selectedDistance = remember { mutableStateOf("Any") }
    val duration = remember { mutableStateOf("More than 2 hours") }
    val popularityFilter = remember { mutableStateOf(PopularityFilter.STAR_RATING_HIGHEST_FIRST) }
    var isChecked by remember { mutableStateOf(true) }
    var rating by remember { mutableStateOf(0) }
    var priceRange by remember { mutableStateOf(0f..100f) }
    LazyColumn( modifier = Modifier
        .fillMaxSize()
        .background(md_theme_light_inverseOnSurface)){
                item {
                    Text(
                        text = "Filter",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        color = md_theme_light_onSurface,
                        modifier = Modifier.padding(16.dp),
                    )

                    Text(
                        text = "Availability",
                        color = md_theme_light_onSurface,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        ),
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    md_theme_light_onPrimary,
                                    RoundedCornerShape(16.dp)
                                ),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "Available Now",
                                color = md_theme_light_onPrimaryContainer,
                                modifier = Modifier.padding(16.dp)
                            )
                            Switch(
                                checked = isChecked,
                                onCheckedChange = { isChecked = it },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = md_theme_light_onPrimary,
                                    checkedTrackColor = md_theme_light_switchColor,
                                    uncheckedThumbColor = md_theme_light_onPrimary,
                                    uncheckedTrackColor = md_theme_light_outline
                                )
                            )
                        }
                    }

                    Text(
                        text = "Sort Options",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        color = md_theme_light_onSurface,
                        modifier = Modifier.padding(16.dp),
                    )
                    FilterSection(
                        popularityFilter = popularityFilter.value,
                        onPopularityFilterChange = { newFilter ->
                            popularityFilter.value = newFilter
                        }
                    )


                    DistanceFilterSection(
                        selectedDistance = selectedDistance.value,
                        onDistanceSelected = { newDistance ->
                            selectedDistance.value = newDistance
                        }

                    )

                    MyRangeSlider(priceRange= priceRange, onRangeChange = { newRange ->
                        priceRange = newRange
                    })
                    DurationPicker(
                        duration = duration.value,
                        onDurationChange = { newDuration ->
                            duration.value = newDuration
                        }
                    )

                    StarSelector(
                        rating = rating,
                        onRatingSelected = { newRating ->
                            rating = newRating
                            // Perform any additional actions with the new rating
                        }
                    )

                    Column (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ){
                        Button(
                            onClick = {
                                val filterOptions = FilterOptions(
                                    sortingOption = when (popularityFilter.value) {
                                        PopularityFilter.STAR_RATING_HIGHEST_FIRST -> "rating"
                                        PopularityFilter.STAR_RATING_LOWEST_FIRST -> "rating"
                                        PopularityFilter.PRICE_LOWEST_FIRST -> "price"
                                        PopularityFilter.PRICE_HIGHEST_FIRST -> "price"
                                        else -> "price"
                                    },
                                    sortingOrder = when (popularityFilter.value) {
                                        PopularityFilter.STAR_RATING_HIGHEST_FIRST -> "DESC"
                                        PopularityFilter.STAR_RATING_LOWEST_FIRST -> "ASCE"
                                        PopularityFilter.PRICE_LOWEST_FIRST -> "ASCE"
                                        PopularityFilter.PRICE_HIGHEST_FIRST -> "DESC"
                                        else -> "DESC"
                                    },
                                    distance = selectedDistance.value,
                                    duration = duration.value,
                                    rating = rating,
                                    priceRange = priceRange
                                )

                                parkingSpaceViewModel.updateFilterOptions(filterOptions)
                                Log.d("-------------FilterOptions", filterOptions.toString())
                                navController.navigate("browse")
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp)
                                .background(
                                    color = md_theme_light_primary,
                                    shape = MaterialTheme.shapes.small
                                ),
                            colors = ButtonDefaults.buttonColors(md_theme_light_primary)
                        ) {
                            Text(text = "Apply Filters")
                        }
                    }


    }
    }
    }


@Composable
fun FilterSection(
    popularityFilter: PopularityFilter,
    onPopularityFilterChange: (PopularityFilter) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        PopularityFilterRadioButtons(
            popularityFilter = popularityFilter,
            onPopularityFilterChange = onPopularityFilterChange
        )
    }
}

@Composable
fun PopularityFilterRadioButtons(
    popularityFilter: PopularityFilter,
    onPopularityFilterChange: (PopularityFilter) -> Unit
) {
    val radioOptions = PopularityFilter.values().toList()

    Column(
        modifier = Modifier
            .padding(8.dp)
            .background(color = md_theme_light_onPrimary, shape = RoundedCornerShape(16.dp))
    ) {
        radioOptions.forEach { option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (popularityFilter == option),
                        onClick = { onPopularityFilterChange(option) }
                    )
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val selectedDistance = remember { mutableStateOf("Any") }
                Text(
                    text = option.displayName,
                    modifier = Modifier.padding(start = 8.dp)
                )
                RadioButton(
                    selected = (popularityFilter == option),
                    onClick = null
                )

            }
        }
    }
}

enum class PopularityFilter(val displayName: String) {
    STAR_RATING_HIGHEST_FIRST("Star Rating (highest first)"),
    STAR_RATING_LOWEST_FIRST("Star Rating (lowest first)"),
    PRICE_LOWEST_FIRST("Price (lowest first)"),
    PRICE_HIGHEST_FIRST("Price (highest first)")
}

@Composable
fun DistanceFilterSection(
    selectedDistance: String,
    onDistanceSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Distance",
            style = MaterialTheme.typography.labelLarge
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DistanceChip(
                distance = "Any",
                isSelected = selectedDistance == "Any",
                onChipClicked = { onDistanceSelected("Any") }
            )

            DistanceChip(
                distance = "Within 1km",
                isSelected = selectedDistance == "Within 1km",
                onChipClicked = { onDistanceSelected("Within 1km") }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DistanceChip(
                distance = "2 - 3 km",
                isSelected = selectedDistance == "2 - 3 km",
                onChipClicked = { onDistanceSelected("2 - 3 km") }
            )

            DistanceChip(
                distance = "4 - 6 km",
                isSelected = selectedDistance == "4 - 6 km",
                onChipClicked = { onDistanceSelected("4 - 6 km") }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DistanceChip(
                distance = "7 - 8 km",
                isSelected = selectedDistance == "7 - 8 km",
                onChipClicked = { onDistanceSelected("7 - 8 km") }
            )

            DistanceChip(
                distance = "9 - 10 km",
                isSelected = selectedDistance == "9 - 10 km",
                onChipClicked = { onDistanceSelected("9 - 10 km") }
            )
        }

    }
}

@Composable
fun DistanceChip(
    distance: String,
    isSelected: Boolean,
    onChipClicked: () -> Unit
) {
    Surface(
        modifier = Modifier
            .clickable { onChipClicked() }
            .border(
                width = 1.dp,
                color = if (isSelected) md_theme_light_primary else md_theme_light_onPrimary,
                shape = MaterialTheme.shapes.small
            )
            .size(170.dp, 55.dp),


        shape = MaterialTheme.shapes.small,
        color = if (isSelected) md_theme_light_onPrimary else md_theme_light_onPrimary,
        contentColor = if (isSelected) md_theme_light_onPrimaryContainer else md_theme_light_outline,

    ) {
        Text(
            text = distance,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(8.dp)
                .wrapContentSize(Alignment.Center),
            fontSize = 15.sp
        )
    }

}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyRangeSlider(priceRange: ClosedFloatingPointRange<Float> = 0f..100f, onRangeChange: (ClosedFloatingPointRange<Float>) -> Unit = {}) {
    Column(modifier= Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        Text(
            text = "Price Range",
            style = MaterialTheme.typography.labelLarge.copy(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            ),
            color = md_theme_light_onSurface,
            modifier = Modifier.padding(16.dp),
        )
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .background(md_theme_light_surface, RoundedCornerShape(16.dp))
                .padding(16.dp),
        ) {

            RangeSlider(
                valueRange = 0f..100f,
                modifier = Modifier
                    .background(md_theme_light_surface)
                    .padding(16.dp),
                value = priceRange,
                colors = SliderDefaults.colors(
                    thumbColor = md_theme_light_onPrimary,
                    activeTrackColor = md_theme_light_primary,
                ),
                onValueChange = { onRangeChange(it) },

                onValueChangeFinished = {
                    // Do something when the user stops changing the value
                }
            )
            Text(
                text = "$${priceRange.start.toInt()} - $${priceRange.endInclusive.toInt()}",
                color = md_theme_light_onBackground
            )
        }
    }
}

@Composable
fun DurationPicker(
    duration: String,
    onDurationChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Duration",
            style = MaterialTheme.typography.labelLarge.copy(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            ),
            color = md_theme_light_onSurface,
            modifier = Modifier.padding(16.dp),
            )
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .background(md_theme_light_surface, RoundedCornerShape(16.dp))
                .padding(16.dp),
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onDurationChange("More than 2 hours") }
                        .padding(start = 15.dp)
                        .background(md_theme_light_surface),
                    horizontalArrangement = Arrangement.SpaceBetween,

                    ) {
                    Text(text = "More than 2 hours")
                    RadioButton(
                        selected = duration == "More than 2 hours",
                        onClick = { onDurationChange("More than 2 hours") }
                    )

                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onDurationChange("1 - 2 hours") }
                        .padding(start = 15.dp)
                        .background(md_theme_light_surface),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(text = "1 - 2 hours")
                    RadioButton(
                        selected = duration == "1 - 2 hours",
                        onClick = { onDurationChange("1 - 2 hours") }
                    )

                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onDurationChange("<1 hour") }
                        .padding(start = 15.dp)
                        .background(md_theme_light_surface),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(text = "<1 hour")
                    RadioButton(
                        selected = duration == "<1 hour",
                        onClick = { onDurationChange("<1 hour") },

                        )

                }
            }
        }
    }
}
@Composable
fun StarSelector(
    rating: Int,
    maxRating: Int = 5,
    onRatingSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Rating",
            style = MaterialTheme.typography.labelLarge.copy(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            ),
            color = md_theme_light_onSurface,
            modifier = Modifier.padding(16.dp),
        )

        Row(
            modifier = Modifier
                .background(md_theme_light_surface, RoundedCornerShape(30.dp))
                .fillMaxWidth()
                .height(40.dp)
                .clickable {
                },
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,


        ) {
            repeat(maxRating) { index ->
                Text(text = "${index + 1}")
                    Icon(
                        imageVector = if (index < rating) Icons.Filled.Star else Icons.Filled.StarOutline,
                        contentDescription = null,
                        tint = md_theme_light_primary,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                onRatingSelected(index + 1)
                            }
                    )
                }
            }
        }
    }






