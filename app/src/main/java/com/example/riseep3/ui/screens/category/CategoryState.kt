package com.example.riseep3.ui.screens.category

data class CategoryState(
    val categories: List<String> = emptyList(),
    val newCategoryName: String = "",
    val isDialogOpen: Boolean = false
)