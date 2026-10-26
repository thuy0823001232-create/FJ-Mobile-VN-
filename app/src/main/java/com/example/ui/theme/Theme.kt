package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val RainbowDarkColorScheme = darkColorScheme(
    primary = RainbowCyan,
    onPrimary = Slate950,
    primaryContainer = Slate800,
    onPrimaryContainer = RainbowCyan,
    secondary = RainbowPurple,
    onSecondary = Color.White,
    secondaryContainer = Slate850,
    onSecondaryContainer = RainbowBlue,
    tertiary = RainbowGreen,
    onTertiary = Slate950,
    background = Slate950,
    onBackground = Slate100,
    surface = Slate900,
    onSurface = Slate100,
    surfaceVariant = Slate800,
    onSurfaceVariant = Slate300,
    outline = Slate700,
    outlineVariant = Slate800,
    error = RainbowRed,
    onError = Color.White
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = RainbowDarkColorScheme,
        typography = Typography,
        content = content
    )
}
