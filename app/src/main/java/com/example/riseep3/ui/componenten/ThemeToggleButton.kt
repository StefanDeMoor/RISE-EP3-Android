package com.example.riseep3.ui.componenten

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.example.riseep3.R

@Composable
fun ThemeToggleButton(
    isDark: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier,
    invertedColors: Boolean = false
) {
    var clicked by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (clicked) 0.9f else 1f,
        animationSpec = tween(durationMillis = 150),
        finishedListener = {
            if (clicked) {
                onToggle()
                clicked = false
            }
        },
        label = "toggleButtonScale"
    )
    val bgColor =
        if (invertedColors) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.onPrimary

    Surface(
        modifier = modifier
            .size(48.dp)
            .scale(scale)
            .testTag("ThemeToggleButton"),
        shape = CircleShape,
        color = bgColor,
        onClick = {
            if (!clicked) {
                clicked = true
            }
        },
        tonalElevation = 4.dp,
        shadowElevation = 6.dp,
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            androidx.compose.foundation.Image(
                painter = painterResource(id = if (isDark) R.drawable.sun_toggle else R.drawable.moon_toggle),
                contentDescription = if (isDark) "Switch to light mode" else "Switch to dark mode",
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ThemeToggleButtonPreview_Light() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        ThemeToggleButton(
            isDark = false,
            onToggle = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ThemeToggleButtonPreview_Dark() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        ThemeToggleButton(
            isDark = true,
            onToggle = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}
