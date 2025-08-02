package com.example.riseep3.ui.componenten.category

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.riseep3.R
import com.example.riseep3.data.category.CategoryEntity
import com.example.riseep3.ui.theme.RiseTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropdownMenu(
    expanded: Boolean,
    selectedCategory: String?,
    categories: List<CategoryEntity>,
    onCategorySelected: (CategoryEntity) -> Unit,
    onExpandedChange: (Boolean) -> Unit
) {
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { onExpandedChange(!expanded) },
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
            onDismissRequest = { onExpandedChange(false) },
            modifier = Modifier
                .exposedDropdownSize()
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.surface,
                )
                .background(MaterialTheme.colorScheme.onBackground)
        ) {
            categories.forEach { category ->
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
                    onClick = { onCategorySelected(category) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryDropdownMenuPreview_Light() {
    RiseTheme(darkTheme = false) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            CategoryDropdownMenuPreviewContent()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryDropdownMenuPreview_Dark() {
    RiseTheme(darkTheme = true) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            CategoryDropdownMenuPreviewContent()
        }
    }
}

@Composable
private fun CategoryDropdownMenuPreviewContent() {
    var expanded by remember { mutableStateOf(true) }
    var selectedCategory by remember { mutableStateOf<CategoryEntity?>(null) }

    val mockCategories = listOf(
        CategoryEntity(name = "Overview"),
        CategoryEntity(name = "Fitness"),
        CategoryEntity(name = "Work"),
        CategoryEntity(name = "Leisure")
    )

    CategoryDropdownMenu(
        expanded = expanded,
        selectedCategory = selectedCategory?.name,
        categories = mockCategories,
        onCategorySelected = {
            selectedCategory = it
            expanded = false
        },
        onExpandedChange = { expanded = it }
    )
}
