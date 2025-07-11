package com.example.riseep3.ui.screens.category

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import com.example.riseep3.ui.componenten.ThemeToggleButton
import com.example.riseep3.ui.theme.Montagu
import com.example.riseep3.ui.theme.RiseTheme
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
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                        .background(MaterialTheme.colorScheme.background)
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
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                    if (category.name.equals("Overview", ignoreCase = true)) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.overview),
                                            contentDescription = "Overview Icon",
                                            tint = MaterialTheme.colorScheme.onBackground
                                        )
                                    }
                                }
                            },
                            onClick = {
                                selectedCategory = category.name
                                expanded = false
                                showNewItemDialog = true
                            }
                        )
                    }
                }
            }

            // Created items
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                createdItems.forEach { (itemName, categoryName) ->
                    var clicked by remember { mutableStateOf(false) }
                    val scale by animateFloatAsState(
                        targetValue = if (clicked) 0.9f else 1f,
                        animationSpec = tween(durationMillis = 150),
                        finishedListener = {
                            if (clicked) {
                                onCategoryClick(categoryName)
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
                                text = itemName,
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
            }

            // Dialog
            if (showNewItemDialog) {
                AlertDialog(
                    onDismissRequest = {
                        newItemName = ""
                        showNewItemDialog = false
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            if (newItemName.isNotBlank() && selectedCategory != null) {
                                createdItems.add(newItemName to selectedCategory!!)
                                newItemName = ""
                                showNewItemDialog = false
                            }
                        }) {
                            Text("Create")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            newItemName = ""
                            showNewItemDialog = false
                        }) {
                            Text("Cancel")
                        }
                    },
                    title = { Text("New Entry") },
                    text = {
                        OutlinedTextField(
                            value = newItemName,
                            onValueChange = { newItemName = it },
                            label = { Text("Item Name") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                )
            }
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
