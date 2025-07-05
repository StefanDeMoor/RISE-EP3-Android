package com.example.riseep3.ui.componenten.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.riseep3.R

@Composable
fun ThemeToggleButton(
    isDark: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.size(48.dp),
        shape = CircleShape,
        color =  if (isDark) colorResource(R.color.teal_700) else colorResource(R.color.fancy_blue),
        onClick = onToggle,
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
                painter = painterResource(id = if (isDark) com.example.riseep3.R.drawable.sun_toggle else com.example.riseep3.R.drawable.moon_toggle),
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
