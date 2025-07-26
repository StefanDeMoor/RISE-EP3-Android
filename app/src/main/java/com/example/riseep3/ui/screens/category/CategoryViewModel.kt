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
import com.example.riseep3.data.overview.OverviewEntity
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

    private val _uiState = MutableStateFlow(CategoryState())
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

    fun addOverview(title: String, categoryName: String) {
        viewModelScope.launch {
            val categoryId = _uiState.value.categories
                .find { it.name == categoryName }
                ?.id ?: 0

            val newId = (_uiState.value.overviews.maxOfOrNull { it.id } ?: 0) + 1

            val newOverview = OverviewEntity(
                id = newId,
                title = title,
                categoryId = categoryId,
                totalIncome = 0.0,
                result = 0.0
            )

            overviewRepo.insertAll(flowOf(listOf(newOverview)))
            loadOverviews()
        }
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
