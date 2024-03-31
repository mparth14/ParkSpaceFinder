package com.parkspace.finder.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * A data class representing spacing values for different sizes.
 * @param extraSmall The extra small spacing value.
 * @param small The small spacing value.
 * @param medium The medium spacing value.
 * @param large The large spacing value.
 * @param extraLarge The extra large spacing value.
 */
data class Spacing(
    val extraSmall: Dp = 4.dp,
    val small: Dp = 8.dp,
    val medium: Dp = 16.dp,
    val large: Dp = 32.dp,
    val extraLarge: Dp = 64.dp
)

/**
 * Composition local for providing spacing values.
 */
val LocalSpacing = compositionLocalOf { Spacing() }

/**
 * Extension property to get the spacing values from MaterialTheme.
 */
val MaterialTheme.spacing: Spacing
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacing.current