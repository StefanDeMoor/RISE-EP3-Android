package com.example.riseep3.ui.componenten.category

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.riseep3.ui.theme.RiseTheme

@Composable
fun AnimatedDialogButton(
    text: String,
    onClick: () -> Unit
) {
    var clicked by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (clicked) 0.9f else 1f,
        animationSpec = tween(durationMillis = 150),
        finishedListener = {
            if (clicked) {
                onClick()
                clicked = false
            }
        },
        label = "dialogButtonScale"
    )

    Surface(
        modifier = Modifier.scale(scale),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 4.dp,
        onClick = {
            if (!clicked) clicked = true
        }
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AnimatedDialogButtonPreview_Light() {
    RiseTheme(darkTheme = false) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            AnimatedDialogButton(
                text = "Confirm",
                onClick = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AnimatedDialogButtonPreview_Dark() {
    RiseTheme(darkTheme = true) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            AnimatedDialogButton(
                text = "Confirm",
                onClick = {}
            )
        }
    }
}