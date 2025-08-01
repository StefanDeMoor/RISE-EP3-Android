package com.example.riseep3.ui.componenten.home

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.riseep3.R
import com.example.riseep3.ui.theme.RiseTheme

@Composable
fun FancyIconButton(
    @DrawableRes icon: Int,
    contentDescription: String,
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
        label = "buttonScale"
    )

    Surface(
        onClick = {
            if (!clicked) {
                clicked = true
            }
        },
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier
            .size(85.dp)
            .scale(scale)
            .testTag(contentDescription),
        tonalElevation = 6.dp,
        shadowElevation = 8.dp
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = contentDescription,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .fillMaxSize(0.6f)
                    .semantics { this.contentDescription = contentDescription }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FancyIconButtonPreview_Light() {
    RiseTheme(darkTheme = false) {
        FancyIconButton(
            icon = R.drawable.plus,
            contentDescription = "Create",
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FancyIconButtonPreview_Dark() {
    RiseTheme(darkTheme = true) {
        FancyIconButton(
            icon = R.drawable.plus,
            contentDescription = "Create",
            onClick = {}
        )
    }
}