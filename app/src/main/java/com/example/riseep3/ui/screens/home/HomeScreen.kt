package com.example.riseep3.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.riseep3.R
import com.example.riseep3.ui.componenten.ThemeToggleButton
import com.example.riseep3.ui.componenten.home.FancyIconButton
import com.example.riseep3.ui.screens.rememberWindowInfo
import com.example.riseep3.ui.theme.Montagu
import com.example.riseep3.ui.theme.RiseTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onCreateClick: () -> Unit,
    onProfileClick: () -> Unit,
    onSalesClick: () -> Unit,
    onProductsClick: () -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    val isDarkTheme by viewModel.isDarkTheme.collectAsState()
    val windowInfo = rememberWindowInfo()
    val isLandscape = windowInfo.isLandscape

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        ThemeToggleButton(
            isDark = isDarkTheme,
            onToggle = viewModel::toggleTheme,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 36.dp, end = 16.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
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
                    fontSize = 40.sp,
                    fontFamily = Montagu,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 44.sp
                )
            )

            val buttons = listOf(
                Triple(Icons.Default.Create, "Create") { onCreateClick() },
                Triple(Icons.Default.Face, "Profile") { onProfileClick() },
                Triple(Icons.Default.ShoppingCart, "Sales") { onSalesClick() },
                Triple(Icons.Default.Menu, "Products") { onProductsClick() }
            )

            if (isLandscape) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(24.dp),
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
                            horizontalArrangement = Arrangement.spacedBy(50.dp),
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
                        lineHeight = 44.sp
                    )
                )
            }

        }
    }
}

@Composable
fun PreviewHomeScreen(isDarkTheme: Boolean) {
    val fakeViewModel = object : HomeViewModel() {
        init {
            _isDarkTheme.value = isDarkTheme
        }
    }

    RiseTheme(darkTheme = isDarkTheme) {
        HomeScreen(
            onCreateClick = {},
            onProfileClick = {},
            onSalesClick = {},
            onProductsClick = {},
            viewModel = fakeViewModel
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
