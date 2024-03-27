package com.parkspace.finder.ui.booking

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun VehicleSelection() {
    val options = listOf(
        "SUV",
        "Hatchback",
        "Minivan",
        "Sedan",
        "Crossover",
        "Coupe",
    )
    var selectedOption = remember { mutableStateOf(options[0]) }
    Column(modifier = Modifier.padding(8.dp)) {
        Text(
            text = "Vehicle Type",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
        )
        FlowRow(modifier = Modifier.selectableGroup()) {
            options.forEach { option ->
                Surface(
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(2.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .clickable { selectedOption.value = option },
                    color = if (selectedOption.value == option) MaterialTheme.colorScheme.primary else Color.White,
                    border = BorderStroke(
                        1.dp,
                        if (selectedOption.value == option) MaterialTheme.colorScheme.primary else Color.LightGray
                    ),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        text = option,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        color = if (selectedOption.value == option) Color.White else Color.Black,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun VehicleSelectionPreview() {
    VehicleSelection()
}