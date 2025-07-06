package com.example.riseep3.ui.screens

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

@Composable
fun rememberWindowInfo(): WindowInfo {
    val configuration = LocalConfiguration.current
    return WindowInfo(
        screenWidthInfo = when {
            configuration.screenWidthDp < 600 -> WindowType.COMPACT
            configuration.screenWidthDp < 840 -> WindowType.MEDIUM
            else -> WindowType.EXPANDED
        },
        screenHeightInfo = when {
            configuration.screenHeightDp < 480 -> WindowType.COMPACT
            configuration.screenHeightDp < 900 -> WindowType.MEDIUM
            else -> WindowType.EXPANDED
        },
        screenWidth = configuration.screenWidthDp.dp,
        screenHeight = configuration.screenHeightDp.dp,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    )
}