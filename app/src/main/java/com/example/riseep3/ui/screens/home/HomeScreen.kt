package com.example.riseep3.ui.screens.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.riseep3.R
import com.example.riseep3.ui.componenten.home.ThemeToggleButton
import com.example.riseep3.ui.theme.Montagu

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onCreateClick: () -> Unit,
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        ThemeToggleButton(
            isDark = isDarkTheme,
            onToggle = onToggleTheme,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 36.dp, end = 16.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome to",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.secondary,
                    letterSpacing = 1.sp
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "HOMECALC",
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = colorResource(R.color.gold),
                    letterSpacing = 2.sp,
                    fontSize = 40.sp,
                    fontFamily = Montagu,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 44.sp
                )
            )

            Spacer(modifier = Modifier.height(48.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(50.dp)) {
                FancyIconButton(
                    icon = Icons.Default.Create,
                    contentDescription = "Create",
                    onClick = onCreateClick,
                )
                FancyIconButton(
                    icon = Icons.Default.Face,
                    contentDescription = "Profile",
                    onClick = { /* TODO: Profile action */ },
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = "Calculate your happiness!",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.secondary,
                    letterSpacing = 1.sp
                )
            )
        }
    }
}

@Composable
fun FancyIconButton(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f,
        label = "buttonScale"
    )

    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(24.dp),
        color = colorResource(R.color.teal_700),
        modifier = Modifier
            .size(85.dp)
            .scale(scale),
        tonalElevation = 6.dp,
        shadowElevation = 8.dp,
        interactionSource = interactionSource
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = Color.White,
                modifier = Modifier.fillMaxSize(0.6f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        onCreateClick = {},
        isDarkTheme = false,
        onToggleTheme = {}
    )
}
