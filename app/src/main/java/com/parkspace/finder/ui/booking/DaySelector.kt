package com.parkspace.finder.ui.booking

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DaySelector() {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = "Day", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(top = 8.dp, bottom = 8.dp))
        Row (horizontalArrangement = Arrangement.Absolute.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Surface(
                modifier = Modifier
                    .defaultMinSize(minWidth = 120.dp)
                    .padding(2.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .clickable {  },
                color = if (false) MaterialTheme.colorScheme.primary else Color.White,
                border = BorderStroke(
                    1.dp,
                    if (false) MaterialTheme.colorScheme.primary else Color.LightGray
                ),
                shape = RoundedCornerShape(5.dp)
            ) {
                Text(
                    text = "Today",
                    modifier = Modifier.padding(horizontal = 22.dp, vertical = 14.dp),
                    color = if (false) Color.White else Color.Black,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )
            }
            Surface(
                modifier = Modifier
                    .defaultMinSize(minWidth = 120.dp)
                    .padding(2.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .clickable {  },
                color = if (false) MaterialTheme.colorScheme.primary else Color.White,
                border = BorderStroke(
                    1.dp,
                    if (false) MaterialTheme.colorScheme.primary else Color.LightGray
                ),
                shape = RoundedCornerShape(5.dp)
            ) {
                Text(
                    text = "Tomorrow",
                    modifier = Modifier.padding(horizontal = 22.dp, vertical = 14.dp),
                    color = if (false) Color.White else Color.Black,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )
            }
            Surface(
                modifier = Modifier
                    .defaultMinSize(minWidth = 120.dp)
                    .padding(2.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .clickable {  },
                color = if (false) MaterialTheme.colorScheme.primary else Color.White,
                border = BorderStroke(
                    1.dp,
                    if (false) MaterialTheme.colorScheme.primary else Color.LightGray
                ),
                shape = RoundedCornerShape(5.dp)
            ) {
                Text(
                    text = "Later",
                    modifier = Modifier.padding(horizontal = 22.dp, vertical = 14.dp),
                    color = if (false) Color.White else Color.Black,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }

}

@Preview
@Composable
fun DaySelectorPreview() {
    DaySelector()
}