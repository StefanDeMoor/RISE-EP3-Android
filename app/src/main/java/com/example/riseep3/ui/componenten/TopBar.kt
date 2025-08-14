package com.example.riseep3.ui.componenten

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TopBar(
    title: String? = null,
    isDarkTheme: Boolean? = null,
    onToggleTheme: (() -> Unit)? = null,
    showBackButton: Boolean = false,
    onNavigateBack: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    showBottomGradient: Boolean = false,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 16.dp, vertical = 24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (showBackButton && onNavigateBack != null) {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.tertiary
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                if (isDarkTheme != null && onToggleTheme != null) {
                    ThemeToggleButton(
                        isDark = isDarkTheme,
                        onToggle = onToggleTheme
                    )
                }

                actions()
            }

            if (!title.isNullOrEmpty()) {
                ScreenTitle(
                    title = title,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        if (showBottomGradient) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.25f),
                                Color.Transparent
                            )
                        )
                    )
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    var isDarkTheme by remember { mutableStateOf(false) }

    TopBar(
        isDarkTheme = isDarkTheme,
        onToggleTheme = { isDarkTheme = !isDarkTheme },
        onNavigateBack = {}
    )
}