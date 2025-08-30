package com.example.riseep3.ui.screens.category

import com.example.riseep3.data.category.CategoryEntity
import com.example.riseep3.data.overview.OverviewEntity

data class CategoryState(
    val categories: List<CategoryEntity> = emptyList(),
    val overviews: List<OverviewEntity> = emptyList(),
    val newCategoryName: String = "",
    val isDialogOpen: Boolean = false,
    val selectedCategory: String? = null,
    val createdItems: List<Pair<String, String>> = emptyList(),
    val showNewItemDialog: Boolean = false,
    val newItemName: String = "",
    val amount: String = "",
    val showSuccessDialog: Boolean = false
)