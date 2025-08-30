package com.example.riseep3.ui.screens.category

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.riseep3.MainApplication
import com.example.riseep3.data.category.CategoryRepository
import com.example.riseep3.data.overview.OverviewEntity
import com.example.riseep3.data.overview.OverviewRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private fun loadOverviews() {
        viewModelScope.launch {
            overviewRepo.getAllOverviews().collect { overviews ->
                _uiState.update { it.copy(overviews = overviews) }
            }
        }
    }

    fun addOverview(title: String, amount: String, categoryName: String) {
        viewModelScope.launch {
            val categoryId = _uiState.value.categories
                .find { it.name == categoryName }
                ?.id ?: 0

            val cleanedAmount = amount.replace(",", ".")
            val amountDouble = cleanedAmount.toDoubleOrNull()

            if (amountDouble == null) {
                Log.e("CategoryViewModel", "Invalid amount input: $amount")
                return@launch
            }

            val newOverview = OverviewEntity(
                id = 0,
                title = title,
                categoryId = categoryId,
                totalIncome = amountDouble,
                result = 0.0
            )

            overviewRepo.insert(newOverview)
        }
    }



    fun selectCategory(categoryName: String) {
        _uiState.update { it.copy(selectedCategory = categoryName) }

        if (categoryName.equals("Overview", ignoreCase = true)) {
            loadOverviews()
        }
    }

    fun addCreatedItem(name: String, categoryName: String) {
        _uiState.update {
            it.copy(createdItems = it.createdItems + (name to categoryName))
        }
    }

    fun clearNewItemName() {
        _uiState.update { it.copy(newItemName = "") }
    }

    fun updateNewItemName(name: String) {
        _uiState.update { it.copy(newItemName = name) }
    }

    fun clearNewItemAmount() {
        _uiState.update { it.copy(amount = "") }
    }

    fun updateNewItemAmount(amount: String) {
        _uiState.update { it.copy(amount = amount) }
    }

    fun setShowNewItemDialog(show: Boolean) {
        _uiState.update { it.copy(isDialogOpen = show) }
    }

    fun setShowSuccessDialog(show: Boolean) {
        _uiState.update { it.copy(showSuccessDialog = show) }
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
