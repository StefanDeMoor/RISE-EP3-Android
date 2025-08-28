package com.example.riseep3.ui.screens.category

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.riseep3.R
import com.example.riseep3.ui.componenten.SuccessDialog
import com.example.riseep3.ui.componenten.TopBar
import com.example.riseep3.ui.componenten.category.NewItemDialog
import com.example.riseep3.ui.componenten.category.CategoryDropdownMenu
import com.example.riseep3.ui.componenten.overview.OverviewCard
import com.example.riseep3.ui.componenten.overview.OverviewSection
import com.example.riseep3.ui.screens.fake.FakeCategoryRepository
import com.example.riseep3.ui.screens.fake.FakeCategoryViewModel
import com.example.riseep3.ui.screens.fake.FakeOverviewRepository
import com.example.riseep3.ui.screens.fake.FakeThemeViewModel
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

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.primary,
        topBar = {
            TopBar(
                title = "Categories",
                isDarkTheme = isDarkTheme,
                onToggleTheme = themeViewModel::toggleTheme,
                showBackButton = true,
                onNavigateBack = onNavigateBack,
                showBottomGradient = true,
                modifier = Modifier.testTag("CategoryScreenTitle")
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            CategoryDropdownMenu(
                expanded = expanded,
                selectedCategory = state.selectedCategory,
                categories = state.categories,
                onCategorySelected = { category ->
                    viewModel.selectCategory(category.name)
                    expanded = false
                },
                onExpandedChange = { expanded = it }
            )

            if (state.selectedCategory.isNullOrBlank()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 140.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.dropdown),
                            contentDescription = "Dropdown illustration",
                            modifier = Modifier.size(80.dp),
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary)
                        )

                        Text(
                            text = "Choose the category you want to work with",
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                if (!state.selectedCategory.isNullOrBlank()) {
                    if (state.selectedCategory.equals("Overview", ignoreCase = true)) {
                        OverviewSection(
                            overviews = state.overviews,
                            createdItems = state.createdItems,
                            onAddClick = { viewModel.setShowNewItemDialog(true) },
                            onCardClick = onCategoryClick
                        )
                    } else {
                        state.createdItems.forEach { (itemName, categoryName) ->
                            if (!categoryName.equals("Overview", ignoreCase = true)) {
                                OverviewCard(
                                    title = itemName,
                                    onClick = { onCategoryClick(itemName) }
                                )
                            }
                        }
                    }
                }
            }

            if (state.isDialogOpen) {
                NewItemDialog(
                    itemName = state.newItemName,
                    onNameChange = viewModel::updateNewItemName,
                    onConfirm = {
                        val selectedCategory = state.selectedCategory ?: return@NewItemDialog
                        val itemName = state.newItemName
                        if (itemName.isNotBlank()) {
                            if (selectedCategory.equals("Overview", ignoreCase = true)) {
                                viewModel.addOverview(itemName, selectedCategory)
                            }
                            viewModel.addCreatedItem(itemName, selectedCategory)
                            viewModel.clearNewItemName()
                            viewModel.setShowNewItemDialog(false)
                            viewModel.setShowSuccessDialog(true)
                        }
                    },
                    onDismiss = {
                        viewModel.clearNewItemName()
                        viewModel.setShowNewItemDialog(false)
                    }
                )
            }

            if (state.showSuccessDialog) {
                SuccessDialog(onDismiss = {
                    viewModel.setShowSuccessDialog(false)
                })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryScreenPreview() {
    val fakeCategoryRepo = remember { FakeCategoryRepository() }
    val fakeOverviewRepo = remember { FakeOverviewRepository() }
    val fakeCategoryVM = remember {
        FakeCategoryViewModel(
            categoryRepo = fakeCategoryRepo,
            overviewRepo = fakeOverviewRepo
        )
    }
    val fakeThemeVM = remember { FakeThemeViewModel() }

    CategoryScreen(
        viewModel = fakeCategoryVM,
        themeViewModel = fakeThemeVM,
        onCategoryClick = {},
        onNavigateBack = {}
    )
}
