package com.example.riseep3.ui.screens.category

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CategoryScreen(
    modifier: Modifier = Modifier,
    viewModel: CategoryViewModel = viewModel(),
    onCategoryClick: (String) -> Unit = {}
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Button(onClick = viewModel::openDialog) {
            Text("Add Category")
        }

        Spacer(modifier = Modifier.height(24.dp))

        state.categories.forEach { category ->
            TextButton(onClick = { onCategoryClick(category) }) {
                Text(text = category, style = MaterialTheme.typography.bodyLarge)
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

@Preview(showBackground = true)
@Composable
fun CategoryScreenPreview() {
    val fakeViewModel = object : CategoryViewModel() {
        init {
            _uiState.value = CategoryState(
                categories = listOf("Books", "Groceries"),
                newCategoryName = "",
                isDialogOpen = false
            )
        }
    }
    CategoryScreen(viewModel = fakeViewModel)
}

