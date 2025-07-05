package com.example.riseep3.ui.screens.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
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
    Box(modifier = modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
    ) {
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
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = MaterialTheme.colorScheme.tertiary,
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

            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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
                Row(horizontalArrangement = Arrangement.spacedBy(50.dp)) {
                    FancyIconButton(
                        icon = Icons.Default.ShoppingCart,
                        contentDescription = "Sales",
                        onClick = { /* TODO: Profile action */ },
                    )
                    FancyIconButton(
                        icon = Icons.Default.Menu,
                        contentDescription = "Products",
                        onClick = { /* TODO: Profile action */ },
                    )
                }
            }


            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = "Calculate at home!",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.tertiary,
                    letterSpacing = 1.sp
                )
            )

            Text(
                text = "For yourself or for your business!",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.tertiary,
                    letterSpacing = 1.sp,
                    lineHeight = 44.sp
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
        color = MaterialTheme.colorScheme.secondary,
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
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.fillMaxSize(0.6f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview_light() {
    HomeScreen(
        onCreateClick = {},
        isDarkTheme = false,
        onToggleTheme = {}
    )
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview_dark() {
    HomeScreen(
        onCreateClick = {},
        isDarkTheme = true,
        onToggleTheme = {}
    )
}
