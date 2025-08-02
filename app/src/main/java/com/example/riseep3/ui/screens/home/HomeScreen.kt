package com.example.riseep3.ui.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.riseep3.R
import com.example.riseep3.ui.componenten.ThemeToggleButton
import com.example.riseep3.ui.componenten.home.FancyIconButton
import com.example.riseep3.ui.screens.rememberWindowInfo
import com.example.riseep3.ui.theme.Montagu
import com.example.riseep3.ui.theme.RiseTheme
import com.example.riseep3.ui.theme.ThemeViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onCreateClick: () -> Unit,
    onProfileClick: () -> Unit,
    onSalesClick: () -> Unit,
    onProductsClick: () -> Unit,
    themeViewModel: ThemeViewModel
) {
    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()
    val windowInfo = rememberWindowInfo()
    val isLandscape = windowInfo.isLandscape

    val headingFontSize = if (isLandscape) 28.sp else 40.sp
    val headingLineHeight = if (isLandscape) 32.sp else 44.sp
    val rowSpacing = if (isLandscape) 24.dp else 50.dp

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 36.dp, end = 16.dp),
                contentAlignment = Alignment.TopEnd
            ) {
                ThemeToggleButton(
                    isDark = isDarkTheme,
                    onToggle = themeViewModel::toggleTheme
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome to",
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = MaterialTheme.colorScheme.tertiary,
                    letterSpacing = 1.sp
                )
            )

            Text(
                text = "HOMECALC",
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = colorResource(R.color.gold),
                    letterSpacing = 2.sp,
                    fontSize = headingFontSize,
                    fontFamily = Montagu,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = headingLineHeight
                )
            )

            val buttons = listOf(
                Triple(R.drawable.plus, "Create") { onCreateClick() },
                Triple(R.drawable.profile, "Profile") { onProfileClick() },
                Triple(R.drawable.sales, "Sales") { onSalesClick() },
                Triple(R.drawable.product, "Products") { onProductsClick() }
            )

            if (isLandscape) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(rowSpacing),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    buttons.forEach { (icon, desc, action) ->
                        FancyIconButton(icon = icon, contentDescription = desc, onClick = action)
                    }
                }
            } else {
                Column(
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    buttons.chunked(2).forEach { rowItems ->
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(rowSpacing),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            rowItems.forEach { (icon, desc, action) ->
                                FancyIconButton(icon = icon, contentDescription = desc, onClick = action)
                            }
                        }
                    }
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Calculate at home!",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.tertiary,
                        letterSpacing = 1.sp
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "For yourself or for your business!",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.tertiary,
                        letterSpacing = 1.sp,
                        lineHeight = if (isLandscape) 28.sp else 44.sp
                    )
                )
            }
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun PreviewHomeScreen(isDarkTheme: Boolean) {
    RiseTheme(darkTheme = isDarkTheme) {
        HomeScreen(
            onCreateClick = {},
            onProfileClick = {},
            onSalesClick = {},
            onProductsClick = {},
            themeViewModel = ThemeViewModel()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview_light() {
    PreviewHomeScreen(isDarkTheme = false)
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview_dark() {
    PreviewHomeScreen(isDarkTheme = true)
}
