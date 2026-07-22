package com.example.eplfixtures.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = EnglandRed,
    secondary = EnglandBlue,
    tertiary = EnglandWhite,
    background = BackgroundLight,
    surface = SurfaceLight,
    onPrimary = Color.White,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
    primaryContainer = EnglandRed.copy(alpha = 0.1f),
    onPrimaryContainer = EnglandRed
)

private val DarkColors = darkColorScheme(
    primary = EnglandRed,
    secondary = EnglandBlue,
    tertiary = EnglandWhite,
    onPrimary = Color.White
)

@Composable
fun EPLFixturesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
