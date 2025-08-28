package com.example.riseep3.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFFFFFFF),   // white
    onPrimary = Color(0xFF25466C), // fancy_blue
    secondary = Color(0xFFFFFFFF),   // white
    onSecondary = Color(0xFF000000),   // black
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF25466C), // fancy_blue
    onPrimary = Color(0xFFFFFFFF),   // white
    secondary = Color(0xFF000000),   // black
    onSecondary = Color(0xFFFFFFFF),   // white
)

@Composable
fun RiseTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}
