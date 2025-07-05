package com.example.riseep3.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6200EE),     // purple_500
    onPrimary = Color(0xFFFFFFFF),   // white
    secondary = Color(0xFF018786),   // teal_700
    onSecondary = Color(0xFF000000), // black
    background = Color(0xFFFFFFFF),  // white
    onBackground = Color(0xFF25466C), // fancy_blue
    surface = Color(0xFFFFFFFF),     // white
    onSurface = Color(0xFF25466C),   // fancy_blue
    tertiary = Color(0xFF25466C), // fancy_blue
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFBB86FC),     // purple_200
    onPrimary = Color(0xFF000000),   // black
    secondary = Color(0xFFFFFFFF), // white
    onSecondary = Color(0xFF000000), // black
    background = Color(0xFF25466C), // fancy_blue
    onBackground = Color(0xFFFFFFFF), // white
    surface = Color(0xFF1F1F1F),
    onSurface = Color(0xFFFFFFFF),
    tertiary = Color(0xFFFFFFFF), // white
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
