package com.example.riseep3.ui.screens.category

import com.example.riseep3.data.category.CategoryEntity

data class CategoryState(
    val categories: List<CategoryEntity> = emptyList(),
    val newCategoryName: String = "",
    val isDialogOpen: Boolean = false
)