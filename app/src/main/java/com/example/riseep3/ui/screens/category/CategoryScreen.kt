package com.example.riseep3.ui.screens.category

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.riseep3.ui.componenten.ScreenTitle
import com.example.riseep3.ui.componenten.SuccessDialog
import com.example.riseep3.ui.componenten.TopBar
import com.example.riseep3.ui.componenten.category.NewItemDialog
import com.example.riseep3.ui.componenten.category.CategoryDropdownMenu
import com.example.riseep3.ui.componenten.overview.OverviewCard
import com.example.riseep3.ui.componenten.overview.OverviewSection
import com.example.riseep3.ui.theme.ThemeViewModel

@Composable
fun CategoryScreen(
    modifier: Modifier = Modifier,
    viewModel: CategoryViewModel = viewModel(factory = CategoryViewModel.Factory),
    onCategoryClick: (String) -> Unit = {},
    themeViewModel: ThemeViewModel,
    onNavigateBack: () -> Unit = {}
) {
    val state by viewModel.uiState.collectAsState()
    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()

    var expanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var showNewItemDialog by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var newItemName by remember { mutableStateOf("") }
    val createdItems = remember { mutableStateListOf<Pair<String, String>>() }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopBar(
                isDarkTheme = isDarkTheme,
                onToggleTheme = themeViewModel::toggleTheme,
                onNavigateBack = onNavigateBack
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

            ScreenTitle()

            CategoryDropdownMenu(
                expanded = expanded,
                selectedCategory = selectedCategory,
                categories = state.categories,
                onCategorySelected = { category ->
                    selectedCategory = category.name
                    expanded = false

                    if (category.name.equals("Overview", ignoreCase = true)) {
                        viewModel.loadOverviews()
                        showNewItemDialog = false
                    } else {
                        showNewItemDialog = true
                    }
                },
                onExpandedChange = { expanded = it }
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                if (selectedCategory.equals("Overview", ignoreCase = true)) {
                    OverviewSection(
                        overviews = state.overviews,
                        createdItems = createdItems,
                        onAddClick = { showNewItemDialog = true },
                        onCardClick = onCategoryClick
                    )
                } else {
                    createdItems.forEach { (itemName, categoryName) ->
                        if (!categoryName.equals("Overview", ignoreCase = true)) {
                            OverviewCard(title = itemName, onClick = { onCategoryClick(categoryName) })
                        }
                    }
                }
            }

            if (showNewItemDialog) {
                NewItemDialog(
                    itemName = newItemName,
                    onNameChange = { newItemName = it },
                    onConfirm = {
                        if (newItemName.isNotBlank() && selectedCategory != null) {
                            if (selectedCategory.equals("Overview", ignoreCase = true)) {
                                viewModel.addOverview(newItemName, selectedCategory!!)
                            }
                            createdItems.add(Pair(newItemName, selectedCategory!!))
                            newItemName = ""
                            showNewItemDialog = false
                            showSuccessDialog = true
                        }
                    },
                    onDismiss = {
                        newItemName = ""
                        showNewItemDialog = false
                    }
                )
            }

            if (showSuccessDialog) {
                SuccessDialog(onDismiss = {
                    showSuccessDialog = false
                })
            }
        }
    }
}
