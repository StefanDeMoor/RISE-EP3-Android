package com.example.riseep3.ui.screens.customer

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.riseep3.ui.componenten.TopBar
import com.example.riseep3.ui.componenten.customer.CustomerSection
import com.example.riseep3.ui.theme.ThemeViewModel
import com.example.riseep3.ui.componenten.customer.SearchBar
import com.example.riseep3.ui.theme.CustomerTheme


@Composable
fun CustomerScreen(
    themeViewModel: ThemeViewModel,
    customerViewModel: CustomerViewModel = viewModel(factory = CustomerViewModel.Factory),
    onNavigateBack: () -> Unit = {},
    onAddCustomerClick: () -> Unit
) {
    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()
    var searchQuery by rememberSaveable { mutableStateOf("") }

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

    CustomerTheme(darkTheme = isDarkTheme) {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.onPrimary,
            topBar = {
                TopBar(
                    title = "Customers",
                    isDarkTheme = isDarkTheme,
                    onToggleTheme = themeViewModel::toggleTheme,
                    showBackButton = true,
                    onNavigateBack = onNavigateBack,
                    showBottomGradient = true,
                    invertedColors = true,
                    modifier = Modifier.testTag("CustomerScreenTitle")
                )
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
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .testTag("LoadingIndicator")
                        )
                    }
                    state.error != null -> {
                        Text(
                            text = state.error ?: "Error",
                            color = Color.Red,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .testTag("ErrorMessage")
                        )
                    }
                    else -> {
                        CustomerSection(
                            customers = state.customers,
                            searchQuery = searchQuery,
                            onAddClick = onAddCustomerClick,
                            onImageClick = { customer ->
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
