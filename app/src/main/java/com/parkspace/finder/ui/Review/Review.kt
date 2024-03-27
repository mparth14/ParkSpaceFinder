package com.parkspace.finder.ui.Review

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.parkspace.finder.StarSelector
import com.parkspace.finder.ui.theme.md_theme_light_primary


@Composable
    @Preview
    fun ReviewScreen() {
        var rating by remember { mutableStateOf(0) }
        var description by remember { mutableStateOf("") }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(1) {
                StarSelector(rating = rating, maxRating = 5, onRatingSelected = { newRating ->
                    rating = newRating

                })
            }
            item {
                OutlinedTextField(
                    value = description,

                    onValueChange = { newDescription ->
                        description = newDescription
                    },

                    label = { Text("Description") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusEvent { }
                )
                   Spacer(Modifier.size(20.dp))
            }
            item {
                Row {
                    Button(onClick = { /*TODO*/ },
                        modifier = Modifier.fillMaxWidth()
                            .background(color = md_theme_light_primary, shape = RoundedCornerShape(50)),
                        colors = ButtonDefaults.buttonColors(md_theme_light_primary),
                        shape = RoundedCornerShape(50)
                    )

                    {
                        Text("Submit")
                    }
                }
            }
        }
    }

