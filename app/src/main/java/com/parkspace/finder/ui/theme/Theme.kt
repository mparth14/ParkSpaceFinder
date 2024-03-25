package com.parkspace.finder.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.parkspace.finder.ui.theme.md_theme_dark_background
import com.parkspace.finder.ui.theme.md_theme_dark_error
import com.parkspace.finder.ui.theme.md_theme_dark_errorContainer
import com.parkspace.finder.ui.theme.md_theme_dark_onBackground
import com.parkspace.finder.ui.theme.md_theme_dark_onError
import com.parkspace.finder.ui.theme.md_theme_dark_onErrorContainer
import com.parkspace.finder.ui.theme.md_theme_dark_onPrimary
import com.parkspace.finder.ui.theme.md_theme_dark_onPrimaryContainer
import com.parkspace.finder.ui.theme.md_theme_dark_onSecondary
import com.parkspace.finder.ui.theme.md_theme_dark_onSecondaryContainer
import com.parkspace.finder.ui.theme.md_theme_dark_onSurface
import com.parkspace.finder.ui.theme.md_theme_dark_onSurfaceVariant
import com.parkspace.finder.ui.theme.md_theme_dark_onTertiary
import com.parkspace.finder.ui.theme.md_theme_dark_onTertiaryContainer
import com.parkspace.finder.ui.theme.md_theme_dark_outline
import com.parkspace.finder.ui.theme.md_theme_dark_primary
import com.parkspace.finder.ui.theme.md_theme_dark_primaryContainer
import com.parkspace.finder.ui.theme.md_theme_dark_secondary
import com.parkspace.finder.ui.theme.md_theme_dark_secondaryContainer
import com.parkspace.finder.ui.theme.md_theme_dark_surface
import com.parkspace.finder.ui.theme.md_theme_dark_surfaceTint
import com.parkspace.finder.ui.theme.md_theme_dark_surfaceVariant
import com.parkspace.finder.ui.theme.md_theme_dark_tertiary
import com.parkspace.finder.ui.theme.md_theme_dark_tertiaryContainer
import com.parkspace.finder.ui.theme.md_theme_light_background
import com.parkspace.finder.ui.theme.md_theme_light_error
import com.parkspace.finder.ui.theme.md_theme_light_errorContainer
import com.parkspace.finder.ui.theme.md_theme_light_onBackground
import com.parkspace.finder.ui.theme.md_theme_light_onError
import com.parkspace.finder.ui.theme.md_theme_light_onErrorContainer
import com.parkspace.finder.ui.theme.md_theme_light_onPrimary
import com.parkspace.finder.ui.theme.md_theme_light_onPrimaryContainer
import com.parkspace.finder.ui.theme.md_theme_light_onSecondary
import com.parkspace.finder.ui.theme.md_theme_light_onSecondaryContainer
import com.parkspace.finder.ui.theme.md_theme_light_onSurface
import com.parkspace.finder.ui.theme.md_theme_light_onSurfaceVariant
import com.parkspace.finder.ui.theme.md_theme_light_onTertiary
import com.parkspace.finder.ui.theme.md_theme_light_onTertiaryContainer
import com.parkspace.finder.ui.theme.md_theme_light_outline
import com.parkspace.finder.ui.theme.md_theme_light_primary
import com.parkspace.finder.ui.theme.md_theme_light_primaryContainer
import com.parkspace.finder.ui.theme.md_theme_light_secondary
import com.parkspace.finder.ui.theme.md_theme_light_secondaryContainer
import com.parkspace.finder.ui.theme.md_theme_light_surface
import com.parkspace.finder.ui.theme.md_theme_light_surfaceTint
import com.parkspace.finder.ui.theme.md_theme_light_surfaceVariant
import com.parkspace.finder.ui.theme.md_theme_light_tertiary
import com.parkspace.finder.ui.theme.md_theme_light_tertiaryContainer
import androidx.compose.ui.viewinterop.AndroidView

private val LightColors = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer,
    onPrimaryContainer = md_theme_light_onPrimaryContainer,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    secondaryContainer = md_theme_light_secondaryContainer,
    onSecondaryContainer = md_theme_light_onSecondaryContainer,
    tertiary = md_theme_light_tertiary,
    onTertiary = md_theme_light_onTertiary,
    tertiaryContainer = md_theme_light_tertiaryContainer,
    onTertiaryContainer = md_theme_light_onTertiaryContainer,
    error = md_theme_light_error,
    errorContainer = md_theme_light_errorContainer,
    onError = md_theme_light_onError,
    onErrorContainer = md_theme_light_onErrorContainer,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
    surfaceVariant = md_theme_light_surfaceVariant,
    onSurfaceVariant = md_theme_light_onSurfaceVariant,
    outline = md_theme_light_outline,
    inverseOnSurface = md_theme_light_inverseOnSurface,
    inverseSurface = md_theme_light_inverseSurface,
    inversePrimary = md_theme_light_inversePrimary,
    surfaceTint = md_theme_light_surfaceTint,
)


private val DarkColors = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer,
    onPrimaryContainer = md_theme_dark_onPrimaryContainer,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    secondaryContainer = md_theme_dark_secondaryContainer,
    onSecondaryContainer = md_theme_dark_onSecondaryContainer,
    tertiary = md_theme_dark_tertiary,
    onTertiary = md_theme_dark_onTertiary,
    tertiaryContainer = md_theme_dark_tertiaryContainer,
    onTertiaryContainer = md_theme_dark_onTertiaryContainer,
    error = md_theme_dark_error,
    errorContainer = md_theme_dark_errorContainer,
    onError = md_theme_dark_onError,
    onErrorContainer = md_theme_dark_onErrorContainer,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
    surfaceVariant = md_theme_dark_surfaceVariant,
    onSurfaceVariant = md_theme_dark_onSurfaceVariant,
    outline = md_theme_dark_outline,
    inverseOnSurface = md_theme_dark_inverseOnSurface,
    inverseSurface = md_theme_dark_inverseSurface,
    inversePrimary = md_theme_dark_inversePrimary,
    surfaceTint = md_theme_dark_surfaceTint,
)

val CustomShapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(8.dp), // Define medium shape
    large = RoundedCornerShape(12.dp)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewPickersTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> darkColorScheme()
        else -> lightColorScheme()
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = CustomShapes,
        content = content
    )
}

@Composable
fun ParkSpaceFinderTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (!useDarkTheme) {
        LightColors
    } else {
        DarkColors
    }

    CompositionLocalProvider(LocalSpacing provides Spacing()) {
        MaterialTheme(
            colorScheme = colors,
            content = content,
            typography = AppTypography,

        )
    }
}