package com.parkspace.finder.ui.booking

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun PriceBottomBar() {
    Box {
        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .zIndex(1f)
                .fillMaxWidth()
                .shadow(elevation= 20.dp),
            color = Color.White,
        ) {
            Row (modifier = Modifier.fillMaxWidth().padding(start = 8.dp, top = 16.dp, end = 8.dp, bottom = 32.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Row {
                    Text(text = "Price")
                    Text(text = "$ 48/hr", modifier = Modifier.padding(start = 8.dp), fontWeight = FontWeight.Bold)
                }
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Book Now", fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
                }
            }
        }
    }
}