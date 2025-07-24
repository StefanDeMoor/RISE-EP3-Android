package com.example.riseep3.ui.screens.category

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.riseep3.MainApplication
import com.example.riseep3.data.category.CategoryEntity
import com.example.riseep3.data.category.CategoryRepository
import com.example.riseep3.data.overview.OverviewRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

open class CategoryViewModel(
    private val categoryRepo: CategoryRepository,
    private val overviewRepo: OverviewRepository
) : ViewModel() {

    val _uiState = MutableStateFlow(CategoryState())
    open val uiState: StateFlow<CategoryState> = _uiState.asStateFlow()

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            categoryRepo.getAllCategories().collect { categories ->
                _uiState.update { it.copy(categories = categories) }
            }
        }
    }

    fun loadOverviews() {
        viewModelScope.launch {
            overviewRepo.getAllOverviews().collect { overviews ->
                _uiState.update { it.copy(overviews = overviews) }
            }
        }
    }

    fun onCategoryNameChange(newName: String) {
        _uiState.update { it.copy(newCategoryName = newName) }
    }

    fun addCategory() {
        val name = _uiState.value.newCategoryName.trim()
        if (name.isNotEmpty()) {
            viewModelScope.launch {
                val newId = _uiState.value.categories.maxOfOrNull { it.id }?.plus(1) ?: 1
                val newCategory = CategoryEntity(id = newId, name = name)
                categoryRepo.insertAll(flowOf(listOf(newCategory)))
                _uiState.update {
                    it.copy(newCategoryName = "", isDialogOpen = false)
                }
            }
        }
    }

    fun openDialog() {
        _uiState.update { it.copy(isDialogOpen = true) }
    }

    fun closeDialog() {
        _uiState.update { it.copy(isDialogOpen = false, newCategoryName = "") }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MainApplication
                CategoryViewModel(app.container.categoryRepository, app.container.overviewRepository)
            }
        }
    }
}
