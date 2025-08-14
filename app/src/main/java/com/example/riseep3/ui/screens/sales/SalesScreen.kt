package com.example.riseep3.ui.screens.sales

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.riseep3.ui.componenten.TopBar
import com.example.riseep3.ui.theme.RiseTheme
import com.example.riseep3.ui.theme.ThemeViewModel

@Composable
fun SalesScreen(
    themeViewModel: ThemeViewModel,
    onNavigateBack: () -> Unit = {}
) {
    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopBar(
                title = "Sales",
                isDarkTheme = isDarkTheme,
                onToggleTheme = themeViewModel::toggleTheme,
                showBackButton = true,
                onNavigateBack = onNavigateBack,
                showBottomGradient = true,
                modifier = Modifier.testTag("SalesScreenTitle")
            )
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

        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview
@Composable
fun SalesScreenPreview() {
    RiseTheme {
        SalesScreen(themeViewModel = ThemeViewModel())
    }
}
