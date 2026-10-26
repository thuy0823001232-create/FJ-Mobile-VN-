package com.example.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Dark Space Backdrop
val Slate950 = Color(0xFF070A10)
val Slate900 = Color(0xFF0F1420)
val Slate850 = Color(0xFF151C2C)
val Slate800 = Color(0xFF1E283D)
val Slate700 = Color(0xFF334155)
val Slate400 = Color(0xFF94A3B8)
val Slate300 = Color(0xFFCBD5E1)
val Slate100 = Color(0xFFF8FAFC)

// Vibrant Rainbow Spectrum
val RainbowRed = Color(0xFFFF1E56)
val RainbowOrange = Color(0xFFFF7300)
val RainbowYellow = Color(0xFFFFDD00)
val RainbowGreen = Color(0xFF00E676)
val RainbowCyan = Color(0xFF00F5D4)
val RainbowBlue = Color(0xFF3A86FF)
val RainbowPurple = Color(0xFF8338EC)
val RainbowPink = Color(0xFFFF007F)

// Social Channels
val YouTubeRed = Color(0xFFFF0000)
val TikTokCyan = Color(0xFF25F4EE)
val TikTokPink = Color(0xFFFE2C55)
val FacebookBlue = Color(0xFF1877F2)

// Rainbow Brushes
val RainbowGradient = Brush.horizontalGradient(
    colors = listOf(
        RainbowRed,
        RainbowOrange,
        RainbowYellow,
        RainbowGreen,
        RainbowCyan,
        RainbowBlue,
        RainbowPurple
    )
)

val RainbowDiagonalGradient = Brush.linearGradient(
    colors = listOf(
        RainbowRed.copy(alpha = 0.8f),
        RainbowYellow.copy(alpha = 0.8f),
        RainbowCyan.copy(alpha = 0.8f),
        RainbowPurple.copy(alpha = 0.8f)
    )
)
