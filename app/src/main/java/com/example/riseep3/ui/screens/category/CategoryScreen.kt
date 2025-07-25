package com.example.riseep3.ui.screens.category

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.riseep3.R
import com.example.riseep3.ui.componenten.SuccessDialog
import com.example.riseep3.ui.componenten.ThemeToggleButton
import com.example.riseep3.ui.componenten.category.NewItemDialog
import com.example.riseep3.ui.theme.Montagu
import com.example.riseep3.ui.theme.ThemeViewModel

@OptIn(ExperimentalMaterial3Api::class)
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 36.dp, start = 8.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                }

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
                .padding(innerPadding)
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Categories",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontFamily = Montagu,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(R.color.gold),
                    letterSpacing = 1.5.sp
                )
            )

            // Dropdown
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = selectedCategory ?: "",
                    onValueChange = {},
                    readOnly = true,
                    label = {
                        Text(
                            text = if (selectedCategory == null) "Select a category" else "Category",
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    placeholder = { Text("Select a category") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface,
                        focusedLabelColor = MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .exposedDropdownSize()
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.surface,
                        )
                        .background(MaterialTheme.colorScheme.onBackground)
                ) {
                    state.categories.forEach { category ->
                        DropdownMenuItem(
                            text = {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = category.name,
                                        color = MaterialTheme.colorScheme.background
                                    )
                                    if (category.name.equals("Overview", ignoreCase = true)) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.overview),
                                            contentDescription = "Overview Icon",
                                            tint = MaterialTheme.colorScheme.background
                                        )
                                    }
                                }
                            },
                            onClick = {
                                selectedCategory = category.name
                                expanded = false

                                if (category.name.equals("Overview", ignoreCase = true)) {
                                    viewModel.loadOverviews()
                                    showNewItemDialog = false
                                } else {
                                    showNewItemDialog = true
                                }
                            }
                        )
                    }
                }
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                if (selectedCategory.equals("Overview", ignoreCase = true)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Overviews",
                            fontWeight = FontWeight.Bold,
                            fontSize = MaterialTheme.typography.titleLarge.fontSize,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        IconButton(
                            onClick = { showNewItemDialog = true },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add Overview",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    state.overviews.forEach { overview ->
                        OverviewCard(title = overview.title, onClick = { onCategoryClick("Overview") })
                    }

                    createdItems.filter { it.second.equals("Overview", ignoreCase = true) }
                        .forEach { (itemName, _) ->
                            OverviewCard(title = itemName, onClick = { onCategoryClick("Overview") })
                        }

                } else {
                    createdItems.forEach { (itemName, categoryName) ->
                        if (categoryName != "Overview") {
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

@Composable
fun OverviewCard(title: String, onClick: () -> Unit) {
    var clicked by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (clicked) 0.9f else 1f,
        animationSpec = tween(durationMillis = 150),
        finishedListener = {
            if (clicked) {
                onClick()
                clicked = false
            }
        },
        label = "cardScale"
    )

    Card(
        onClick = {
            if (!clicked) clicked = true
        },
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onBackground
        ),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.background
                )
            )

            Icon(
                painter = painterResource(id = R.drawable.overview),
                contentDescription = "Overview Icon",
                tint = MaterialTheme.colorScheme.background
            )
        }
    }
}



//@SuppressLint("ViewModelConstructorInComposable")
//@Preview(showBackground = true)
//@Composable
//fun CategoryScreenPreview_Light() {
//    CategoryScreen(themeViewModel = ThemeViewModel())
//}
//@SuppressLint("ViewModelConstructorInComposable")
//@Preview(showBackground = true)
//@Composable
//fun CategoryScreenPreview_Dark() {
//    CategoryScreen(themeViewModel = ThemeViewModel())
//}
