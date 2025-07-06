package com.example.riseep3.ui.screens

import androidx.compose.ui.unit.Dp

data class WindowInfo(
    val screenWidthInfo: WindowType,
    val screenHeightInfo: WindowType,
    val screenWidth: Dp,
    val screenHeight: Dp,
    val isLandscape: Boolean
)

enum class WindowType {
    COMPACT, MEDIUM, EXPANDED
}