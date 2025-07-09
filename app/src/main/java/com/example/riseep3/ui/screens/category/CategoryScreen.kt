package com.example.riseep3.ui.screens.category

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
fun CategoryScreen(
    modifier: Modifier = Modifier,
    viewModel: CategoryViewModel = viewModel(factory = CategoryViewModel.Factory),
    onCategoryClick: (String) -> Unit = {},
    onNavigateBack: () -> Unit = {}
) {
    val state by viewModel.uiState.collectAsState()
    val isDarkTheme by viewModel.isDarkTheme.collectAsState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 36.dp),
            ) {
                IconButton(
                    onClick = onNavigateBack,
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }

                ThemeToggleButton(
                    isDark = isDarkTheme,
                    onToggle = viewModel::toggleTheme,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Button(onClick = viewModel::openDialog) {
                Text("Add Category")
            }

            Spacer(modifier = Modifier.height(24.dp))

            state.categories.forEach { category ->
                TextButton(onClick = { onCategoryClick(category.name) }) {
                    Text(text = category.name, style = MaterialTheme.typography.bodyLarge)
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            if (state.isDialogOpen) {
                AlertDialog(
                    onDismissRequest = viewModel::closeDialog,
                    confirmButton = {
                        TextButton(onClick = viewModel::addCategory) {
                            Text("Add")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = viewModel::closeDialog) {
                            Text("Cancel")
                        }
                    },
                    title = { Text("New Category") },
                    text = {
                        OutlinedTextField(
                            value = state.newCategoryName,
                            onValueChange = viewModel::onCategoryNameChange,
                            label = { Text("Category Name") }
                        )
                    }
                )
            }
        }
    }
}


//@Composable
//fun PreviewCategoryScreen(isDarkTheme: Boolean) {
//    val fakeViewModel = object : CategoryViewModel() {
//        init {
//            _uiState.value = CategoryState(
//                categories = listOf("Books", "Groceries"),
//                newCategoryName = "",
//                isDialogOpen = false
//            )
//            _isDarkTheme.value = isDarkTheme
//        }
//    }
//
//    RiseTheme(darkTheme = isDarkTheme) {
//        CategoryScreen(viewModel = fakeViewModel)
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun CategoryScreenPreview_Light() {
//    PreviewCategoryScreen(isDarkTheme = false)
//}
//
//@Preview(showBackground = true)
//@Composable
//fun CategoryScreenPreview_Dark() {
//    PreviewCategoryScreen(isDarkTheme = true)
//}
