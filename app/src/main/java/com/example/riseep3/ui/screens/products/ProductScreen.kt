package com.example.riseep3.ui.screens.products

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.riseep3.ui.componenten.ScreenTitle
import com.example.riseep3.ui.componenten.ThemeToggleButton
import com.example.riseep3.ui.theme.RiseTheme
import com.example.riseep3.ui.theme.ThemeViewModel

@Composable
fun ProductScreen(
    themeViewModel: ThemeViewModel,
    onNavigateBack: () -> Unit = {}
) {
    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 36.dp)
            ) {
                IconButton(
                    onClick = onNavigateBack,
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }

                ThemeToggleButton(
                    isDark = isDarkTheme,
                    onToggle = themeViewModel::toggleTheme,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ScreenTitle(
                title = "Products",
                modifier = Modifier.testTag("ProductScreenTitle")
            )
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview
@Composable
fun ProductScreenPreview() {
    RiseTheme {
        ProductScreen(themeViewModel = ThemeViewModel())
    }
}
