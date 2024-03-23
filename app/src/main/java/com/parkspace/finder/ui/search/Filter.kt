package com.parkspace.finder.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.RangeSlider
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.parkspace.finder.ui.theme.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Text
import androidx.compose.runtime.*

import androidx.compose.ui.draw.alpha

import androidx.compose.ui.res.painterResource

import androidx.compose.ui.unit.dp
import androidx.core.util.toRange
import androidx.navigation.NavHostController
import com.parkspace.finder.data.AuthViewModel

@Composable
@Preview
fun FilterSection() {
// viewModel: AuthViewModel?, navController: NavHostController
    val selectedDistance = remember { mutableStateOf("Any") }
    val duration = remember { mutableStateOf("More than 2 hours") }
    val onDurationChange = remember { mutableStateOf("More than 2 hours") }
    val popularityFilter = remember { mutableStateOf(PopularityFilter.STAR_RATING_HIGHEST_FIRST) }
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
                            text = "Available Today",
                            color = md_theme_light_onPrimaryContainer,
                            modifier = Modifier.padding(16.dp)
                        )
                        Switch(
                            checked = true,
                            onCheckedChange = {  },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = md_theme_light_onPrimary,
                                checkedTrackColor = md_theme_light_switchColor,
                                uncheckedThumbColor = md_theme_light_onPrimary,
                                uncheckedTrackColor = md_theme_light_outline
                            )
                        )
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

                    MyRangeSlider()
                    DurationPicker(
                        duration = duration.value,
                        onDurationChange = { newDuration ->
                            duration.value = newDuration
                        }
                    )



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
    POUPULARITY("Popularity"),
    STAR_RATING_HIGHEST_FIRST("Star Rating (highest first)"),
    STAR_RATING_LOWEST_FIRST("Star Rating (lowest first)"),
    BEST_REVIEWED_FIRST("Best Reviewed First"),
    MOST_REVIEWED_FIRST("Most Reviewed First"),
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
fun MyRangeSlider() {
    var sliderRange by remember { mutableStateOf(0f..100f) }
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
                value = sliderRange,
                colors = SliderDefaults.colors(
                    thumbColor = md_theme_light_onPrimary,
                    activeTrackColor = md_theme_light_primary,
                ),
                onValueChange = { sliderRange = it },

                onValueChangeFinished = {
                    // Do something when the user stops changing the value
                }
            )
            Text(
                text = "$${sliderRange.start.toInt()} - $${sliderRange.endInclusive.toInt()}",
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
            .background(md_theme_light_onPrimary, RoundedCornerShape(16.dp))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onDurationChange("More than 2 hours") },
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
                .clickable { onDurationChange("1 - 2 hours") },
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
                .clickable { onDurationChange("<1 hour") },
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



