package com.example.riseep3.ui.screens.category

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

open class CategoryViewModel : ViewModel() {
    val _uiState = MutableStateFlow(CategoryState())
    val uiState: StateFlow<CategoryState> = _uiState

    fun onCategoryNameChange(newName: String) {
        _uiState.value = _uiState.value.copy(newCategoryName = newName)
    }

    fun addCategory() {
        val name = _uiState.value.newCategoryName.trim()
        if (name.isNotEmpty()) {
            _uiState.value = _uiState.value.copy(
                categories = _uiState.value.categories + name,
                newCategoryName = "",
                isDialogOpen = false
            )
        }
    }

    fun openDialog() {
        _uiState.value = _uiState.value.copy(isDialogOpen = true)
    }

    fun closeDialog() {
        _uiState.value = _uiState.value.copy(isDialogOpen = false, newCategoryName = "")
    }
}
