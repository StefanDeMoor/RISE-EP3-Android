package com.example.riseep3.ui.screens.customer

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.riseep3.ui.componenten.ScreenTitle
import com.example.riseep3.ui.componenten.ThemeToggleButton
import com.example.riseep3.ui.componenten.customer.CustomerCard
import com.example.riseep3.ui.theme.ThemeViewModel
import com.example.riseep3.ui.componenten.customer.SearchBar


@Composable
fun CustomerScreen(
    themeViewModel: ThemeViewModel,
    customerViewModel: CustomerViewModel = viewModel(factory = CustomerViewModel.Factory),
    onNavigateBack: () -> Unit = {}
) {
    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    val state by customerViewModel.state.collectAsState()

    var selectedCustomerId by remember { mutableStateOf<Int?>(null) }

    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            selectedCustomerId?.let { id ->
                val localPath = customerViewModel.saveImageLocally(context, it)
                customerViewModel.updateProfileImage(id, localPath)
            }
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            Box {
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
                        IconButton(onClick = onNavigateBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        ThemeToggleButton(
                            isDark = isDarkTheme,
                            onToggle = themeViewModel::toggleTheme
                        )
                    }

                    ScreenTitle(
                        title = "Customers",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .testTag("CustomerScreenTitle")
                    )
                }

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
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it }
            )

            when {
                state.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }
                state.error != null -> {
                    Text(
                        text = state.error ?: "Error",
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
                else -> {
                    state.customers
                        .filter {
                            "${it.firstName} ${it.lastName}".contains(searchQuery, ignoreCase = true)
                        }
                        .forEach { customer ->
                            CustomerCard(
                                name = "${customer.firstName} ${customer.lastName}",
                                phoneNumber = customer.phoneNumber,
                                profileImagePath = customer.profileImagePath,
                                onImageClick = {
                                    // Zet huidige klant-ID en start image picker
                                    selectedCustomerId = customer.id
                                    launcher.launch("image/*")
                                }
                            )
                        }
                }
            }
        }
    }
}
