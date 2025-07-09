package com.example.riseep3.ui.screens.products

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.riseep3.ui.componenten.ThemeToggleButton
import com.example.riseep3.ui.theme.RiseTheme

@Composable
fun ProductScreen(
    viewModel: ProductViewModel = viewModel(),
    onNavigateBack: () -> Unit = {}
) {
    val isDarkTheme by viewModel.isDarkTheme.collectAsState()

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
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }

                ThemeToggleButton(
                    isDark = isDarkTheme,
                    onToggle = viewModel::toggleTheme,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Text("Products")
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview
@Composable
fun ProductScreenPreview() {
    val previewViewModel = ProductViewModel().apply {
        _isDarkTheme.value = false
    }
    RiseTheme {
        ProductScreen(viewModel = previewViewModel)
    }
}
