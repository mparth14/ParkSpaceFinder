package com.parkspace.finder.ui.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.parkspace.finder.ui.theme.spacing

/**
 * Composable function to display the header for authentication screens.
 * @param headingText The text to display as the header.
 */
@Composable
fun AuthHeader(headingText: String) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally)
    {
        val spacing = MaterialTheme.spacing

        Text(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = spacing.medium, start = spacing.large),
            text = headingText,
            style = MaterialTheme.typography.headlineLarge,
            lineHeight = 48.sp,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Left,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}