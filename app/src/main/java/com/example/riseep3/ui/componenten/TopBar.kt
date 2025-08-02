package com.example.riseep3.ui.componenten

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TopBar(
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit,
    onNavigateBack: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 36.dp, start = 8.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = onNavigateBack) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.tertiary
            )
        }

        ThemeToggleButton(
            isDark = isDarkTheme,
            onToggle = onToggleTheme
        )
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